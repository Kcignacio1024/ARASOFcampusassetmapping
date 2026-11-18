package ph.edu.batstateu.arasof.arasofcampusassetmapping.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isShiftPressed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

/**
 * A viewport you can zoom and move around:
 *  - touch: pinch to zoom, drag to move
 *  - laptop: mouse wheel / touchpad scroll zooms at the cursor, drag moves, Shift + wheel moves sideways
 *  - small + / − / reset buttons in the corner
 * [content] receives the current zoom factor (1 = fit) so it can keep text / icons a constant size.
 */
@Composable
fun ZoomPanBox(
    modifier: Modifier = Modifier,
    maxZoom: Float = 10f,
    content: @Composable BoxScope.(scale: Float) -> Unit
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var boxSize by remember { mutableStateOf(IntSize.Zero) }

    fun clamp(o: Offset, s: Float, size: IntSize): Offset {
        val mx = size.width * (s - 1f) / 2f
        val my = size.height * (s - 1f) / 2f
        return Offset(o.x.coerceIn(-mx, mx), o.y.coerceIn(-my, my))
    }

    fun zoomAt(focus: Offset, factor: Float) {
        val c = Offset(boxSize.width / 2f, boxSize.height / 2f)
        val ns = (scale * factor).coerceIn(1f, maxZoom)
        val rel = focus - c
        offset = clamp(rel - (rel - offset) * (ns / scale), ns, boxSize)
        scale = ns
    }

    Box(
        modifier
            .clipToBounds()
            .onSizeChanged { boxSize = it }
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, _ ->
                    val c = Offset(size.width / 2f, size.height / 2f)
                    val old = scale
                    val ns = (old * zoom).coerceIn(1f, maxZoom)
                    val rel = centroid - c
                    val moved = rel - (rel - offset) * (ns / old) + pan
                    scale = ns
                    offset = clamp(moved, ns, size)
                }
            }
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        if (event.type == PointerEventType.Scroll) {
                            val ch = event.changes.first()
                            val d = ch.scrollDelta
                            if (event.keyboardModifiers.isShiftPressed) {
                                val move = if (d.x != 0f) d.x else d.y
                                offset = clamp(offset + Offset(-move * 60f, 0f), scale, size)
                                ch.consume()
                            } else if (d.y != 0f) {
                                zoomAt(ch.position, if (d.y < 0f) 1.2f else 1f / 1.2f)
                                ch.consume()
                            }
                        }
                    }
                }
            }
    ) {
        Box(
            Modifier.fillMaxSize().graphicsLayer(
                scaleX = scale, scaleY = scale,
                translationX = offset.x, translationY = offset.y
            )
        ) { content(scale) }

        Column(
            Modifier.align(Alignment.BottomEnd).padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val mid = Offset(boxSize.width / 2f, boxSize.height / 2f)
            RoundButton("+") { zoomAt(mid, 1.5f) }
            RoundButton("−") { zoomAt(mid, 1f / 1.5f) }
            RoundButton("⟲") { scale = 1f; offset = Offset.Zero }
        }
    }
}

@Composable
private fun RoundButton(label: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.size(36.dp).clip(CircleShape).clickable(onClick = onClick),
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f),
        shadowElevation = 3.dp
    ) {
        Box(contentAlignment = Alignment.Center) { Text(label, style = MaterialTheme.typography.titleMedium) }
    }
}