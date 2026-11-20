package ph.edu.batstateu.arasof.arasofcampusassetmapping.data

import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.*

data class FloorData(
    val rooms: List<Room>,
    val assets: List<Asset>,
    val zones: List<Zone> = emptyList(),
    /** width / height of the floor-plan drawing */
    val aspect: Float = 1.2f,
    /** small note shown above the plan (e.g. "placeholder layout") */
    val note: String? = null
)

object CampusData {
    /** Reference size of the map. The current picture (res/drawable/campus_map.png) is 884 x 1080, the same
     *  shape, so these numbers are kept and the tap areas scale with the picture. If you swap in a bigger or
     *  smaller picture of the same shape you can keep them too. */
    const val MAP_W = 1860f
    const val MAP_H = 2270f

    // Each building's tap area ("shapes") was traced from the map picture, in picture pixels.
    // Buildings 1-25 are the numbers printed on the map. 27+ are the things in the legend:
    // electrical boxes (27-29), segregation stations (30-31), guard houses (32-38) and water tanks (39-46).
    // They are information only (no floor plan). Id 26 is not used any more (that electrical box is not
    // on the current map).
    val buildings = listOf(
        Building(
            1, "Ibaan Building", Category.MIXED_USE, 1280f, 2040f, 287f, 109f, floorCount = 2, roomsPerRow = 3,
            shapes = listOf(
                listOf(1287f to 2046f, 1280f to 2140f, 1286f to 2144f, 1543f to 2149f, 1567f to 2131f, 1565f to 2051f, 1560f to 2047f, 1291f to 2040f)
            )
        ),
        Building(
            2, "VIP Corals - Nasugbu Marine Research Station", Category.RESEARCH, 1644f, 2045f, 175f, 95f, floorCount = 1, roomsPerRow = 2,
            shapes = listOf(
                listOf(1819f to 2103f, 1796f to 2099f, 1794f to 2073f, 1783f to 2068f, 1782f to 2045f, 1697f to 2046f, 1695f to 2056f, 1680f to 2067f, 1677f to 2089f, 1670f to 2092f, 1670f to 2098f, 1662f to 2104f, 1644f to 2104f, 1644f to 2139f, 1716f to 2135f, 1797f to 2140f, 1818f to 2136f)
            )
        ),
        Building(
            3, "Taal Building", Category.MIXED_USE, 1630f, 1661f, 148f, 293f, floorCount = 4, roomsPerRow = 3,
            shapes = listOf(
                listOf(1754f to 1661f, 1648f to 1672f, 1630f to 1678f, 1631f to 1703f, 1638f to 1715f, 1644f to 1812f, 1662f to 1817f, 1664f to 1827f, 1677f to 1836f, 1677f to 1892f, 1682f to 1900f, 1697f to 1904f, 1693f to 1919f, 1672f to 1920f, 1664f to 1926f, 1666f to 1949f, 1671f to 1954f, 1761f to 1944f, 1762f to 1913f, 1778f to 1906f, 1771f to 1692f, 1760f to 1683f)
            )
        ),
        Building(
            4, "Rosario Cafeteria", Category.AUXILIARY, 1313f, 1738f, 141f, 149f, floorCount = 2, roomsPerRow = 2,
            shapes = listOf(
                listOf(1446f to 1738f, 1401f to 1742f, 1394f to 1748f, 1371f to 1752f, 1355f to 1785f, 1313f to 1788f, 1317f to 1857f, 1368f to 1850f, 1375f to 1887f, 1435f to 1876f, 1432f to 1841f, 1447f to 1827f, 1448f to 1775f, 1454f to 1772f)
            )
        ),
        Building(
            5, "Apacible Museum", Category.ADMINISTRATION, 1083f, 1786f, 190f, 127f, floorCount = 2, roomsPerRow = 2,
            shapes = listOf(
                listOf(1093f to 1786f, 1093f to 1834f, 1083f to 1879f, 1088f to 1902f, 1136f to 1902f, 1147f to 1911f, 1186f to 1913f, 1192f to 1907f, 1256f to 1909f, 1260f to 1905f, 1268f to 1853f, 1273f to 1845f, 1271f to 1821f, 1222f to 1819f, 1213f to 1828f, 1197f to 1830f, 1169f to 1821f, 1128f to 1819f, 1122f to 1813f, 1124f to 1786f)
            )
        ),
        Building(
            6, "Sto. Tomas Building", Category.ACADEMIC, 883f, 1453f, 209f, 679f, floorCount = 3, roomsPerRow = 3, status = BuildingStatus.UNDER_CONSTRUCTION,
            shapes = listOf(
                listOf(1074f to 1453f, 1053f to 1456f, 1046f to 1463f, 1034f to 1461f, 1033f to 1455f, 1011f to 1459f, 1013f to 1481f, 995f to 1490f, 997f to 1499f, 993f to 1506f, 998f to 1506f, 999f to 1511f, 991f to 1513f, 997f to 1522f, 993f to 1523f, 994f to 1532f, 986f to 1537f, 991f to 1552f, 982f to 1556f, 987f to 1569f, 983f to 1574f, 985f to 1583f, 976f to 1587f, 973f to 1607f, 978f to 1608f, 979f to 1615f, 971f to 1618f, 974f to 1627f, 969f to 1628f, 969f to 1632f, 975f to 1633f, 969f to 1656f, 963f to 1656f, 960f to 1676f, 964f to 1677f, 964f to 1686f, 957f to 1687f, 961f to 1697f, 955f to 1698f, 960f to 1710f, 950f to 1713f, 950f to 1717f, 959f to 1719f, 959f to 1730f, 954f to 1737f, 954f to 1749f, 944f to 1755f, 944f to 1759f, 950f to 1760f, 949f to 1772f, 945f to 1773f, 947f to 1782f, 939f to 1785f, 943f to 1803f, 938f to 1818f, 945f to 1837f, 941f to 1839f, 940f to 1858f, 932f to 1859f, 937f to 1876f, 932f to 1891f, 926f to 1892f, 931f to 1904f, 929f to 1918f, 917f to 1921f, 912f to 1943f, 920f to 1945f, 920f to 1962f, 908f to 1972f, 907f to 1991f, 899f to 1992f, 905f to 1998f, 903f to 2012f, 899f to 2013f, 901f to 2023f, 893f to 2024f, 897f to 2029f, 894f to 2057f, 886f to 2058f, 889f to 2067f, 884f to 2068f, 889f to 2085f, 885f to 2087f, 883f to 2103f, 893f to 2111f, 913f to 2109f, 1009f to 2132f, 1020f to 2128f, 1021f to 2123f, 1027f to 2124f, 1028f to 2129f, 1042f to 2124f, 1052f to 2127f, 1061f to 2117f, 1061f to 2097f, 1069f to 2095f, 1069f to 2088f, 1082f to 2082f, 1078f to 2076f, 1080f to 2057f, 1069f to 2053f, 1068f to 2046f, 1052f to 2048f, 1043f to 2046f, 1040f to 2040f, 1022f to 2042f, 1016f to 2035f, 1003f to 2036f, 1002f to 2032f, 993f to 2034f, 992f to 2030f, 984f to 2029f, 989f to 2021f, 987f to 2012f, 991f to 2011f, 989f to 2002f, 993f to 2001f, 991f to 1992f, 999f to 1990f, 997f to 1971f, 1003f to 1968f, 998f to 1963f, 1000f to 1942f, 1009f to 1938f, 1009f to 1934f, 1003f to 1933f, 1007f to 1927f, 1007f to 1907f, 1012f to 1900f, 1049f to 1904f, 1051f to 1890f, 1055f to 1889f, 1053f to 1880f, 1061f to 1839f, 1069f to 1836f, 1064f to 1831f, 1064f to 1820f, 1069f to 1815f, 1073f to 1776f, 1081f to 1773f, 1069f to 1752f, 1052f to 1753f, 1042f to 1742f, 1044f to 1716f, 1051f to 1715f, 1046f to 1710f, 1047f to 1696f, 1055f to 1694f, 1051f to 1676f, 1059f to 1673f, 1059f to 1669f, 1053f to 1668f, 1054f to 1658f, 1063f to 1654f, 1058f to 1637f, 1067f to 1631f, 1062f to 1626f, 1068f to 1590f, 1075f to 1589f, 1069f to 1580f, 1079f to 1526f, 1087f to 1523f, 1081f to 1514f, 1092f to 1469f, 1085f to 1464f, 1084f to 1455f, 1075f to 1460f)
            )
        ),
        Building(
            7, "Joson Gymnasium", Category.ACADEMIC, 1133f, 1445f, 195f, 365f, floorCount = 2, roomsPerRow = 2,
            shapes = listOf(
                listOf(1160f to 1445f, 1133f to 1739f, 1172f to 1742f, 1173f to 1747f, 1151f to 1749f, 1150f to 1799f, 1270f to 1810f, 1275f to 1803f, 1277f to 1759f, 1271f to 1751f, 1302f to 1751f, 1328f to 1457f, 1315f to 1453f)
            )
        ),
        Building(
            8, "Infirmary", Category.ALLIED, 1431f, 1475f, 103f, 233f, floorCount = 1, roomsPerRow = 2,
            shapes = listOf(
                listOf(1448f to 1475f, 1437f to 1482f, 1431f to 1708f, 1498f to 1708f, 1500f to 1661f, 1504f to 1657f, 1498f to 1621f, 1505f to 1612f, 1505f to 1565f, 1510f to 1560f, 1510f to 1539f, 1534f to 1532f, 1532f to 1478f)
            )
        ),
        Building(
            9, "Ermita Building (combined with Hatchery & Research Building)", Category.ADMINISTRATION, 1686f, 1432f, 134f, 174f, floorCount = 2, roomsPerRow = 2,
            shapes = listOf(
                listOf(1816f to 1432f, 1767f to 1432f, 1766f to 1437f, 1727f to 1435f, 1726f to 1441f, 1699f to 1439f, 1692f to 1441f, 1686f to 1452f, 1691f to 1468f, 1692f to 1517f, 1753f to 1517f, 1754f to 1511f, 1764f to 1511f, 1765f to 1606f, 1815f to 1606f, 1820f to 1601f, 1820f to 1457f)
            )
        ),
        Building(
            10, "Villadolid Ground Powerhouse", Category.OTHERS, 1648f, 1082f, 94f, 90f, floorCount = 1, roomsPerRow = 0,
            shapes = listOf(
                listOf(1648f to 1082f, 1742f to 1082f, 1742f to 1172f, 1648f to 1172f)
            )
        ),
        Building(
            11, "IMT Building / Lobo Building (combined with the Library)", Category.ACADEMIC, 1690f, 863f, 124f, 198f, floorCount = 2, roomsPerRow = 3,
            shapes = listOf(
                listOf(1800f to 863f, 1690f to 875f, 1702f to 1061f, 1814f to 1049f)
            )
        ),
        Building(
            12, "Talisay Mini Forest", Category.OTHERS, 1180f, 1110f, 350f, 270f, floorCount = 1, roomsPerRow = 0,
            shapes = listOf(
                listOf(1180f to 1110f, 1530f to 1110f, 1530f to 1380f, 1180f to 1380f)
            )
        ),
        Building(
            13, "Lipa Building", Category.ACADEMIC, 1044f, 793f, 233f, 579f, floorCount = 4, roomsPerRow = 3,
            shapes = listOf(
                listOf(1259f to 804f, 1068f to 793f, 1068f to 850f, 1082f to 853f, 1086f to 865f, 1084f to 886f, 1065f to 892f, 1064f to 937f, 1070f to 949f, 1071f to 969f, 1058f to 1037f, 1059f to 1074f, 1069f to 1099f, 1052f to 1149f, 1055f to 1192f, 1050f to 1198f, 1051f to 1233f, 1044f to 1282f, 1045f to 1321f, 1059f to 1324f, 1063f to 1331f, 1063f to 1358f, 1051f to 1365f, 1053f to 1372f, 1123f to 1370f, 1128f to 1363f, 1137f to 1167f, 1149f to 1123f, 1144f to 1037f, 1151f to 898f, 1156f to 892f, 1274f to 895f, 1277f to 865f, 1268f to 856f, 1268f to 809f)
            )
        ),
        Building(
            14, "Student Council Building", Category.GENERAL, 1062f, 644f, 62f, 134f, floorCount = 1, roomsPerRow = 2,
            shapes = listOf(
                listOf(1062f to 652f, 1100f to 644f, 1124f to 768f, 1086f to 778f)
            )
        ),
        Building(
            15, "Calatagan Building", Category.ACADEMIC, 1101f, 536f, 283f, 153f, floorCount = 3, roomsPerRow = 3,
            shapes = listOf(
                listOf(1103f to 536f, 1101f to 602f, 1218f to 612f, 1227f to 630f, 1237f to 630f, 1240f to 620f, 1256f to 616f, 1313f to 621f, 1320f to 626f, 1321f to 687f, 1377f to 689f, 1384f to 590f, 1382f to 564f, 1353f to 562f, 1350f to 551f, 1297f to 544f, 1250f to 543f, 1247f to 553f, 1169f to 549f, 1159f to 544f, 1158f to 536f)
            )
        ),
        Building(
            16, "Lemery Building", Category.ACADEMIC, 1111f, 68f, 299f, 470f, floorCount = 2, roomsPerRow = 3,
            shapes = listOf(
                listOf(1115f to 280f, 1111f to 296f, 1112f to 336f, 1119f to 342f, 1163f to 339f, 1182f to 346f, 1204f to 340f, 1271f to 342f, 1271f to 284f, 1186f to 278f),
                listOf(1410f to 73f, 1362f to 68f, 1354f to 71f, 1349f to 161f, 1333f to 168f, 1333f to 182f, 1347f to 189f, 1344f to 249f, 1348f to 276f, 1339f to 391f, 1325f to 392f, 1318f to 387f, 1317f to 380f, 1297f to 379f, 1290f to 388f, 1150f to 381f, 1144f to 451f, 1141f to 456f, 1122f to 456f, 1119f to 461f, 1117f to 520f, 1195f to 526f, 1203f to 530f, 1205f to 537f, 1219f to 538f, 1222f to 522f, 1202f to 520f, 1199f to 515f, 1203f to 499f, 1235f to 499f, 1247f to 512f, 1375f to 516f, 1378f to 456f, 1391f to 449f)
            )
        ),
        Building(
            17, "Maintenance Building", Category.GENERAL, 1128f, 92f, 78f, 72f, floorCount = 1, roomsPerRow = 2,
            shapes = listOf(
                listOf(1128f to 92f, 1206f to 92f, 1206f to 164f, 1128f to 164f)
            )
        ),
        Building(
            18, "Open Court", Category.OTHERS, 808f, 706f, 112f, 174f, floorCount = 1, roomsPerRow = 0,
            shapes = listOf(
                listOf(808f to 706f, 920f to 706f, 920f to 880f, 808f to 880f)
            )
        ),
        Building(
            19, "Lian Cafeteria", Category.AUXILIARY, 639f, 1151f, 232f, 126f, floorCount = 1, roomsPerRow = 2,
            shapes = listOf(
                listOf(639f to 1193f, 644f to 1241f, 655f to 1242f, 662f to 1252f, 681f to 1254f, 691f to 1261f, 709f to 1262f, 718f to 1251f, 754f to 1255f, 767f to 1273f, 806f to 1277f, 814f to 1270f, 816f to 1261f, 825f to 1266f, 855f to 1265f, 860f to 1244f, 869f to 1243f, 871f to 1223f, 861f to 1216f, 865f to 1176f, 811f to 1169f, 804f to 1161f, 770f to 1165f, 654f to 1151f, 648f to 1192f)
            )
        ),
        Building(
            20, "Nasugbu Hostel", Category.MIXED_USE, 391f, 1328f, 265f, 266f, floorCount = 3, roomsPerRow = 3,
            shapes = listOf(
                listOf(447f to 1328f, 412f to 1498f, 399f to 1514f, 391f to 1544f, 414f to 1554f, 594f to 1594f, 623f to 1594f, 628f to 1571f, 624f to 1540f, 656f to 1394f, 656f to 1375f, 641f to 1368f, 468f to 1329f)
            )
        ),
        Building(
            21, "San Juan Building", Category.ACADEMIC, 619f, 1371f, 213f, 684f, floorCount = 3, roomsPerRow = 3,
            shapes = listOf(
                listOf(777f to 1371f, 767f to 1371f, 762f to 1377f, 756f to 1422f, 735f to 1503f, 710f to 1635f, 702f to 1641f, 682f to 1641f, 670f to 1687f, 661f to 1745f, 671f to 1750f, 672f to 1759f, 660f to 1808f, 666f to 1834f, 647f to 1904f, 648f to 1915f, 619f to 2051f, 633f to 2055f, 635f to 2047f, 643f to 2043f, 690f to 2050f, 800f to 1563f, 832f to 1402f, 787f to 1389f, 784f to 1375f)
            )
        ),
        Building(
            22, "Batangan Student Services Center", Category.MIXED_USE, 84f, 1509f, 278f, 518f, floorCount = 3, roomsPerRow = 3, status = BuildingStatus.UNBUILT,
            shapes = listOf(
                listOf(216f to 1509f, 215f to 1513f, 202f to 1513f, 205f to 1526f, 198f to 1527f, 194f to 1541f, 197f to 1558f, 193f to 1570f, 186f to 1571f, 181f to 1605f, 176f to 1606f, 180f to 1616f, 172f to 1620f, 173f to 1629f, 168f to 1630f, 173f to 1641f, 158f to 1665f, 161f to 1683f, 156f to 1691f, 150f to 1691f, 156f to 1700f, 148f to 1702f, 144f to 1716f, 148f to 1726f, 140f to 1730f, 144f to 1740f, 130f to 1761f, 135f to 1772f, 132f to 1782f, 124f to 1784f, 127f to 1799f, 117f to 1804f, 108f to 1836f, 112f to 1850f, 103f to 1852f, 97f to 1872f, 102f to 1884f, 94f to 1888f, 98f to 1898f, 91f to 1921f, 84f to 1922f, 84f to 1926f, 88f to 1927f, 85f to 1950f, 108f to 1955f, 121f to 1970f, 121f to 1985f, 135f to 1985f, 137f to 2001f, 153f to 1998f, 165f to 2002f, 169f to 2011f, 174f to 2006f, 183f to 2007f, 184f to 2015f, 226f to 2027f, 229f to 2021f, 235f to 2021f, 242f to 1987f, 250f to 1985f, 245f to 1975f, 248f to 1969f, 255f to 1969f, 251f to 1964f, 253f to 1947f, 273f to 1941f, 278f to 1936f, 273f to 1927f, 291f to 1921f, 292f to 1913f, 301f to 1912f, 299f to 1891f, 306f to 1890f, 303f to 1877f, 311f to 1876f, 302f to 1863f, 307f to 1847f, 302f to 1837f, 304f to 1832f, 311f to 1832f, 318f to 1798f, 314f to 1792f, 323f to 1789f, 318f to 1778f, 327f to 1775f, 322f to 1770f, 322f to 1765f, 328f to 1764f, 326f to 1746f, 335f to 1745f, 330f to 1734f, 347f to 1726f, 349f to 1715f, 344f to 1714f, 344f to 1708f, 353f to 1704f, 355f to 1692f, 350f to 1691f, 350f to 1686f, 361f to 1675f, 362f to 1660f, 356f to 1651f, 359f to 1621f, 351f to 1611f, 357f to 1587f, 353f to 1567f, 305f to 1549f, 293f to 1528f, 276f to 1526f, 275f to 1531f, 266f to 1530f, 263f to 1521f, 243f to 1516f, 232f to 1519f, 231f to 1513f)
            )
        ),
        Building(
            23, "Roxas Ground Facade", Category.GENERAL, 96f, 2082f, 166f, 50f, floorCount = 1, roomsPerRow = 0,
            shapes = listOf(
                listOf(96f to 2082f, 262f to 2082f, 262f to 2132f, 96f to 2132f)
            )
        ),
        Building(
            24, "RGO Commercial Rental Stall", Category.OTHERS, 262f, 2038f, 94f, 92f, floorCount = 1, roomsPerRow = 0,
            shapes = listOf(
                listOf(262f to 2038f, 356f to 2038f, 356f to 2130f, 262f to 2130f)
            )
        ),
        Building(
            25, "Entrance-Exit (Guardhouse)", Category.GENERAL, 356f, 2000f, 234f, 132f, floorCount = 1, roomsPerRow = 0,
            shapes = listOf(
                listOf(356f to 2000f, 540f to 2000f, 590f to 2040f, 590f to 2126f, 480f to 2132f, 356f to 2132f)
            )
        ),
        Building(
            27, "Electrical box 1", Category.FACILITY, 1387f, 1007f, 60f, 60f, floorCount = 1, roomsPerRow = 0, hasPlan = false, info = "Electrical box / electrical asset.",
            shapes = listOf(
                listOf(1447f to 1037f, 1442f to 1052f, 1432f to 1062f, 1417f to 1067f, 1402f to 1062f, 1391f to 1052f, 1387f to 1037f, 1391f to 1022f, 1402f to 1011f, 1417f to 1007f, 1432f to 1011f, 1442f to 1022f)
            )
        ),
        Building(
            28, "Electrical box 2", Category.FACILITY, 1477f, 1327f, 60f, 60f, floorCount = 1, roomsPerRow = 0, hasPlan = false, info = "Electrical box / electrical asset.",
            shapes = listOf(
                listOf(1537f to 1357f, 1532f to 1372f, 1522f to 1382f, 1507f to 1387f, 1492f to 1382f, 1481f to 1372f, 1477f to 1357f, 1481f to 1342f, 1492f to 1331f, 1507f to 1327f, 1522f to 1331f, 1532f to 1342f)
            )
        ),
        Building(
            29, "Electrical box 3", Category.FACILITY, 702f, 2066f, 60f, 60f, floorCount = 1, roomsPerRow = 0, hasPlan = false, info = "Electrical box / electrical asset.",
            shapes = listOf(
                listOf(762f to 2096f, 757f to 2111f, 747f to 2121f, 732f to 2126f, 717f to 2121f, 706f to 2111f, 702f to 2096f, 706f to 2081f, 717f to 2070f, 732f to 2066f, 747f to 2070f, 757f to 2081f)
            )
        ),
        Building(
            30, "Segregation Station 1", Category.FACILITY, 647f, 914f, 60f, 60f, floorCount = 1, roomsPerRow = 0, hasPlan = false, info = "Waste segregation station.",
            shapes = listOf(
                listOf(707f to 944f, 702f to 959f, 692f to 969f, 677f to 974f, 662f to 969f, 651f to 959f, 647f to 944f, 651f to 929f, 662f to 918f, 677f to 914f, 692f to 918f, 702f to 929f)
            )
        ),
        Building(
            31, "Segregation Station 2", Category.FACILITY, 889f, 1017f, 60f, 60f, floorCount = 1, roomsPerRow = 0, hasPlan = false, info = "Waste segregation station.",
            shapes = listOf(
                listOf(949f to 1047f, 944f to 1062f, 934f to 1072f, 919f to 1077f, 904f to 1072f, 893f to 1062f, 889f to 1047f, 893f to 1032f, 904f to 1021f, 919f to 1017f, 934f to 1021f, 944f to 1032f)
            )
        ),
        Building(
            32, "Guard house 1", Category.FACILITY, 858f, 1367f, 60f, 60f, floorCount = 1, roomsPerRow = 0, hasPlan = false, info = "Guard house.",
            shapes = listOf(
                listOf(918f to 1397f, 913f to 1412f, 903f to 1422f, 888f to 1427f, 873f to 1422f, 862f to 1412f, 858f to 1397f, 862f to 1382f, 873f to 1371f, 888f to 1367f, 903f to 1371f, 913f to 1382f)
            )
        ),
        Building(
            33, "Guard house 2", Category.FACILITY, 988f, 1325f, 60f, 60f, floorCount = 1, roomsPerRow = 0, hasPlan = false, info = "Guard house.",
            shapes = listOf(
                listOf(1048f to 1355f, 1043f to 1370f, 1033f to 1380f, 1018f to 1385f, 1003f to 1380f, 992f to 1370f, 988f to 1355f, 992f to 1340f, 1003f to 1329f, 1018f to 1325f, 1033f to 1329f, 1043f to 1340f)
            )
        ),
        Building(
            34, "Guard house 3", Category.FACILITY, 290f, 2060f, 60f, 60f, floorCount = 1, roomsPerRow = 0, hasPlan = false, info = "Guard house.",
            shapes = listOf(
                listOf(350f to 2090f, 345f to 2105f, 335f to 2115f, 320f to 2120f, 305f to 2115f, 294f to 2105f, 290f to 2090f, 294f to 2075f, 305f to 2064f, 320f to 2060f, 335f to 2064f, 345f to 2075f)
            )
        ),
        Building(
            35, "Guard house 4", Category.FACILITY, 327f, 2081f, 60f, 60f, floorCount = 1, roomsPerRow = 0, hasPlan = false, info = "Guard house.",
            shapes = listOf(
                listOf(387f to 2111f, 382f to 2126f, 372f to 2136f, 357f to 2141f, 342f to 2136f, 331f to 2126f, 327f to 2111f, 331f to 2096f, 342f to 2085f, 357f to 2081f, 372f to 2085f, 382f to 2096f)
            )
        ),
        Building(
            36, "Guard house 5", Category.FACILITY, 493f, 2056f, 60f, 60f, floorCount = 1, roomsPerRow = 0, hasPlan = false, info = "Guard house.",
            shapes = listOf(
                listOf(553f to 2086f, 548f to 2101f, 538f to 2111f, 523f to 2116f, 508f to 2111f, 497f to 2101f, 493f to 2086f, 497f to 2071f, 508f to 2060f, 523f to 2056f, 538f to 2060f, 548f to 2071f)
            )
        ),
        Building(
            37, "Guard house 6", Category.FACILITY, 1223f, 2106f, 60f, 60f, floorCount = 1, roomsPerRow = 0, hasPlan = false, info = "Guard house.",
            shapes = listOf(
                listOf(1283f to 2136f, 1278f to 2151f, 1268f to 2161f, 1253f to 2166f, 1238f to 2161f, 1227f to 2151f, 1223f to 2136f, 1227f to 2121f, 1238f to 2110f, 1253f to 2106f, 1268f to 2110f, 1278f to 2121f)
            )
        ),
        Building(
            38, "Guard house 7", Category.FACILITY, 1539f, 2131f, 60f, 60f, floorCount = 1, roomsPerRow = 0, hasPlan = false, info = "Guard house.",
            shapes = listOf(
                listOf(1599f to 2161f, 1594f to 2176f, 1584f to 2186f, 1569f to 2191f, 1554f to 2186f, 1543f to 2176f, 1539f to 2161f, 1543f to 2146f, 1554f to 2135f, 1569f to 2131f, 1584f to 2135f, 1594f to 2146f)
            )
        ),
        Building(
            39, "Water tank 1", Category.FACILITY, 1078f, 625f, 44f, 44f, floorCount = 1, roomsPerRow = 0, hasPlan = false, info = "Water tank (next to the Student Council Building (north)).",
            shapes = listOf(
                listOf(1122f to 647f, 1119f to 658f, 1111f to 666f, 1100f to 669f, 1089f to 666f, 1081f to 658f, 1078f to 647f, 1081f to 636f, 1089f to 628f, 1100f to 625f, 1111f to 628f, 1119f to 636f)
            )
        ),
        Building(
            40, "Water tank 2", Category.FACILITY, 1174f, 713f, 82f, 82f, floorCount = 1, roomsPerRow = 0, hasPlan = false, info = "Water tank (large tank next to the Student Council Building).",
            shapes = listOf(
                listOf(1174f to 713f, 1256f to 713f, 1256f to 794f, 1174f to 794f)
            )
        ),
        Building(
            41, "Water tank 3", Category.FACILITY, 1068f, 756f, 44f, 44f, floorCount = 1, roomsPerRow = 0, hasPlan = false, info = "Water tank (next to the Student Council Building (south)).",
            shapes = listOf(
                listOf(1112f to 778f, 1109f to 789f, 1101f to 797f, 1090f to 800f, 1079f to 797f, 1071f to 789f, 1068f to 778f, 1071f to 767f, 1079f to 759f, 1090f to 756f, 1101f to 759f, 1109f to 767f)
            )
        ),
        Building(
            42, "Water tank 4", Category.FACILITY, 757f, 1113f, 44f, 44f, floorCount = 1, roomsPerRow = 0, hasPlan = false, info = "Water tank (next to the Lian Cafeteria).",
            shapes = listOf(
                listOf(801f to 1135f, 798f to 1146f, 790f to 1154f, 779f to 1157f, 768f to 1154f, 759f to 1146f, 757f to 1135f, 759f to 1124f, 768f to 1116f, 779f to 1113f, 790f to 1116f, 798f to 1124f)
            )
        ),
        Building(
            43, "Water tank 5", Category.FACILITY, 1417f, 1456f, 44f, 44f, floorCount = 1, roomsPerRow = 0, hasPlan = false, info = "Water tank (next to the Infirmary).",
            shapes = listOf(
                listOf(1461f to 1478f, 1458f to 1489f, 1450f to 1497f, 1439f to 1500f, 1428f to 1497f, 1420f to 1489f, 1417f to 1478f, 1420f to 1467f, 1428f to 1459f, 1439f to 1456f, 1450f to 1459f, 1458f to 1467f)
            )
        ),
        Building(
            44, "Water tank 6", Category.FACILITY, 809f, 1691f, 44f, 44f, floorCount = 1, roomsPerRow = 0, hasPlan = false, info = "Water tank (beside the San Juan Building (north)).",
            shapes = listOf(
                listOf(853f to 1713f, 850f to 1724f, 842f to 1732f, 831f to 1735f, 820f to 1732f, 812f to 1724f, 809f to 1713f, 812f to 1702f, 820f to 1694f, 831f to 1691f, 842f to 1694f, 850f to 1702f)
            )
        ),
        Building(
            45, "Water tank 7", Category.FACILITY, 790f, 1737f, 44f, 44f, floorCount = 1, roomsPerRow = 0, hasPlan = false, info = "Water tank (beside the San Juan Building (south)).",
            shapes = listOf(
                listOf(834f to 1759f, 831f to 1770f, 823f to 1778f, 812f to 1781f, 801f to 1778f, 793f to 1770f, 790f to 1759f, 793f to 1748f, 801f to 1740f, 812f to 1737f, 823f to 1740f, 831f to 1748f)
            )
        ),
        Building(
            46, "Water tank 8", Category.FACILITY, 1552f, 2065f, 44f, 44f, floorCount = 1, roomsPerRow = 0, hasPlan = false, info = "Water tank (next to the Ibaan Building).",
            shapes = listOf(
                listOf(1596f to 2087f, 1593f to 2098f, 1585f to 2106f, 1574f to 2109f, 1563f to 2106f, 1555f to 2098f, 1552f to 2087f, 1555f to 2076f, 1563f to 2068f, 1574f to 2065f, 1585f to 2068f, 1593f to 2076f)
            )
        )
    )

    private val planProviders = mapOf(
        1 to Building1Plan::forFloor,
        2 to Building2Plan::forFloor,
        3 to Building3Plan::forFloor,
        4 to Building4Plan::forFloor,
        5 to Building5Plan::forFloor,
        7 to Building7Plan::forFloor,
        8 to Building8Plan::forFloor,
        9 to Building9Plan::forFloor,
        10 to Building10Plan::forFloor,
        11 to Building11Plan::forFloor,
        13 to Building13Plan::forFloor,
        14 to Building14Plan::forFloor,
        15 to Building15Plan::forFloor,
        16 to Building16Plan::forFloor,
        17 to Building17Plan::forFloor,
        18 to Building18Plan::forFloor,
        19 to Building19Plan::forFloor,
        20 to Building20Plan::forFloor,
        21 to Building21Plan::forFloor,
        23 to Building23Plan::forFloor
    )

    private val cache = mutableMapOf<Pair<Int, Floor>, FloorData>()

    fun floorData(b: Building, floor: Floor): FloorData =
        cache.getOrPut(b.id to floor) {
            planProviders[b.id]?.invoke(b.id, floor) ?: generate(b, floor)
        }

    /**
     * Generates a rough layout: 2 rows of rooms with a corridor between them.
     * Replace with real data (JSON / database) when you have the actual floor plans.
     */
    private fun generate(b: Building, floor: Floor): FloorData {
        val rooms = mutableListOf<Room>()
        val assets = mutableListOf<Asset>()
        val prefix = "B${b.id}-${floor.ordinal}"

        // Outdoor / open areas: no rooms, just a few safety assets
        if (b.roomsPerRow == 0) {
            assets += Asset("$prefix-HY", b.id, floor, AssetType.HYDRANT, 0.3f, 0.5f, "Fire hydrant · ${b.name}")
            assets += Asset("$prefix-FL", b.id, floor, AssetType.FIRE_LIGHT, 0.5f, 0.5f, "Fire light · ${b.name}")
            assets += Asset("$prefix-FX", b.id, floor, AssetType.EXTINGUISHER, 0.7f, 0.5f, "Fire extinguisher · ${b.name}")
            return FloorData(rooms, assets)
        }

        val perRow = b.roomsPerRow
        val digit = floor.ordinal + 1

        for (row in 0..1) for (i in 0 until perRow) {
            val k = row * perRow + i + 1
            val rw = 0.9f / perRow
            val rx = 0.05f + i * rw
            val ry = if (row == 0) 0.06f else 0.58f
            val rh = 0.36f
            val isLab = (b.id + floor.ordinal + k) % 3 == 0          // some rooms have no computers
            val computers = if (isLab) 20 + ((b.id + k) % 3) * 10 else 0
            val rid = "$prefix-R$k"
            val name = "Room $digit${k.toString().padStart(2, '0')}"

            rooms += Room(rid, b.id, floor, name, rx, ry, rw, rh, computers)
            assets += Asset("$rid-FA", b.id, floor, AssetType.FIRE_ALARM,
                rx + rw * 0.25f, ry + rh * 0.68f, "Fire alarm · $name", rid)
            assets += Asset("$rid-FL", b.id, floor, AssetType.FIRE_LIGHT,
                rx + rw * 0.75f, ry + rh * 0.68f, "Fire light · $name", rid)
            if (computers > 0) {
                assets += Asset("$rid-PC", b.id, floor, AssetType.COMPUTER,
                    rx + rw * 0.5f, ry + rh * 0.38f, "Computers ($computers) · $name", rid)
            }
        }

        // Corridor assets
        assets += Asset("$prefix-EX1", b.id, floor, AssetType.EXIT, 0.03f, 0.5f, "Exit (west) · ${b.name}")
        assets += Asset("$prefix-EX2", b.id, floor, AssetType.EXIT, 0.97f, 0.5f, "Exit (east) · ${b.name}")
        assets += Asset("$prefix-CFL", b.id, floor, AssetType.FIRE_LIGHT, 0.25f, 0.5f, "Corridor fire light · ${b.name}")
        assets += Asset("$prefix-CFA", b.id, floor, AssetType.FIRE_ALARM, 0.75f, 0.5f, "Corridor fire alarm · ${b.name}")
        if (floor == Floor.GROUND) {
            assets += Asset("$prefix-HY", b.id, floor, AssetType.HYDRANT, 0.5f, 0.5f, "Fire hydrant · ${b.name}")
        } else {
            assets += Asset("$prefix-FX", b.id, floor, AssetType.EXTINGUISHER, 0.5f, 0.5f, "Fire extinguisher · ${b.name} ${floor.label}")
        }
        return FloorData(
            rooms, assets,
            zones = listOf(Zone("Corridor", ZoneKind.CORRIDOR, 0.05f, 0.42f, 0.9f, 0.16f)),
            aspect = 1.2f,
            note = "Placeholder layout. The real floor plan has not been added yet."
        )
    }
}