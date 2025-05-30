package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AddTest {
    @Test
	public void test1() throws Exception {
		assertEquals(1, original.Add.add(0, 1));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(1, original.Add.add(1, 0));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(5, original.Add.add(2, 3));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(12, original.Add.add(5, 7));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(12, original.Add.add(7, 5));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(1297, original.Add.add(572, 725));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(855, original.Add.add(51, 804));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(741, original.Add.add(645, 96));
	}

	@Test
	public void test9() throws Exception {
		assertEquals(1565, original.Add.add(712, 853));
	}

	@Test
	public void test10() throws Exception {
		assertEquals(324, original.Add.add(223, 101));
	}

	@Test
	public void test11() throws Exception {
		assertEquals(105, original.Add.add(76, 29));
	}

	@Test
	public void test12() throws Exception {
		assertEquals(565, original.Add.add(416, 149));
	}

	@Test
	public void test13() throws Exception {
		assertEquals(554, original.Add.add(145, 409));
	}

	@Test
	public void test14() throws Exception {
		assertEquals(965, original.Add.add(535, 430));
	}

	@Test
	public void test15() throws Exception {
		assertEquals(421, original.Add.add(118, 303));
	}

	@Test
	public void test16() throws Exception {
		assertEquals(381, original.Add.add(287, 94));
	}

	@Test
	public void test17() throws Exception {
		assertEquals(1025, original.Add.add(768, 257));
	}

	@Test
	public void test18() throws Exception {
		assertEquals(1098, original.Add.add(421, 677));
	}

	@Test
	public void test19() throws Exception {
		assertEquals(1616, original.Add.add(802, 814));
	}

	@Test
	public void test20() throws Exception {
		assertEquals(1432, original.Add.add(510, 922));
	}

	@Test
	public void test21() throws Exception {
		assertEquals(1164, original.Add.add(345, 819));
	}

	@Test
	public void test22() throws Exception {
		assertEquals(1331, original.Add.add(895, 436));
	}

	@Test
	public void test23() throws Exception {
		assertEquals(547, original.Add.add(123, 424));
	}

	@Test
	public void test24() throws Exception {
		assertEquals(1168, original.Add.add(923, 245));
	}

	@Test
	public void test25() throws Exception {
		assertEquals(461, original.Add.add(23, 438));
	}

	@Test
	public void test26() throws Exception {
		assertEquals(698, original.Add.add(565, 133));
	}

	@Test
	public void test27() throws Exception {
		assertEquals(1870, original.Add.add(945, 925));
	}

	@Test
	public void test28() throws Exception {
		assertEquals(1244, original.Add.add(261, 983));
	}

	@Test
	public void test29() throws Exception {
		assertEquals(716, original.Add.add(139, 577));
	}

	@Test
	public void test30() throws Exception {
		assertEquals(941, original.Add.add(763, 178));
	}

	@Test
	public void test31() throws Exception {
		assertEquals(1039, original.Add.add(147, 892));
	}

	@Test
	public void test32() throws Exception {
		assertEquals(838, original.Add.add(436, 402));
	}

	@Test
	public void test33() throws Exception {
		assertEquals(1191, original.Add.add(610, 581));
	}

	@Test
	public void test34() throws Exception {
		assertEquals(519, original.Add.add(103, 416));
	}

	@Test
	public void test35() throws Exception {
		assertEquals(1329, original.Add.add(339, 990));
	}

	@Test
	public void test36() throws Exception {
		assertEquals(634, original.Add.add(130, 504));
	}

	@Test
	public void test37() throws Exception {
		assertEquals(959, original.Add.add(242, 717));
	}

	@Test
	public void test38() throws Exception {
		assertEquals(672, original.Add.add(562, 110));
	}

	@Test
	public void test39() throws Exception {
		assertEquals(1305, original.Add.add(396, 909));
	}

	@Test
	public void test40() throws Exception {
		assertEquals(1590, original.Add.add(887, 703));
	}

	@Test
	public void test41() throws Exception {
		assertEquals(1421, original.Add.add(870, 551));
	}

	@Test
	public void test42() throws Exception {
		assertEquals(813, original.Add.add(422, 391));
	}

	@Test
	public void test43() throws Exception {
		assertEquals(804, original.Add.add(299, 505));
	}

	@Test
	public void test44() throws Exception {
		assertEquals(402, original.Add.add(346, 56));
	}

	@Test
	public void test45() throws Exception {
		assertEquals(742, original.Add.add(36, 706));
	}

	@Test
	public void test46() throws Exception {
		assertEquals(1149, original.Add.add(738, 411));
	}

	@Test
	public void test47() throws Exception {
		assertEquals(766, original.Add.add(679, 87));
	}

	@Test
	public void test48() throws Exception {
		assertEquals(328, original.Add.add(25, 303));
	}

	@Test
	public void test49() throws Exception {
		assertEquals(773, original.Add.add(161, 612));
	}

	@Test
	public void test50() throws Exception {
		assertEquals(1147, original.Add.add(306, 841));
	}

	@Test
	public void test51() throws Exception {
		assertEquals(1384, original.Add.add(973, 411));
	}

	@Test
	public void test52() throws Exception {
		assertEquals(868, original.Add.add(711, 157));
	}

	@Test
	public void test53() throws Exception {
		assertEquals(498, original.Add.add(471, 27));
	}

	@Test
	public void test54() throws Exception {
		assertEquals(1506, original.Add.add(714, 792));
	}

	@Test
	public void test55() throws Exception {
		assertEquals(244, original.Add.add(38, 206));
	}

	@Test
	public void test56() throws Exception {
		assertEquals(1250, original.Add.add(907, 343));
	}

	@Test
	public void test57() throws Exception {
		assertEquals(783, original.Add.add(23, 760));
	}

	@Test
	public void test58() throws Exception {
		assertEquals(1383, original.Add.add(524, 859));
	}

	@Test
	public void test59() throws Exception {
		assertEquals(559, original.Add.add(30, 529));
	}

	@Test
	public void test60() throws Exception {
		assertEquals(1032, original.Add.add(341, 691));
	}

	@Test
	public void test61() throws Exception {
		assertEquals(896, original.Add.add(167, 729));
	}

	@Test
	public void test62() throws Exception {
		assertEquals(925, original.Add.add(636, 289));
	}

	@Test
	public void test63() throws Exception {
		assertEquals(647, original.Add.add(503, 144));
	}

	@Test
	public void test64() throws Exception {
		assertEquals(1036, original.Add.add(51, 985));
	}

	@Test
	public void test65() throws Exception {
		assertEquals(436, original.Add.add(287, 149));
	}

	@Test
	public void test66() throws Exception {
		assertEquals(734, original.Add.add(659, 75));
	}

	@Test
	public void test67() throws Exception {
		assertEquals(1259, original.Add.add(462, 797));
	}

	@Test
	public void test68() throws Exception {
		assertEquals(547, original.Add.add(406, 141));
	}

	@Test
	public void test69() throws Exception {
		assertEquals(150, original.Add.add(106, 44));
	}

	@Test
	public void test70() throws Exception {
		assertEquals(1234, original.Add.add(300, 934));
	}

	@Test
	public void test71() throws Exception {
		assertEquals(995, original.Add.add(471, 524));
	}

	@Test
	public void test72() throws Exception {
		assertEquals(551, original.Add.add(122, 429));
	}

	@Test
	public void test73() throws Exception {
		assertEquals(930, original.Add.add(735, 195));
	}

	@Test
	public void test74() throws Exception {
		assertEquals(819, original.Add.add(335, 484));
	}

	@Test
	public void test75() throws Exception {
		assertEquals(837, original.Add.add(28, 809));
	}

	@Test
	public void test76() throws Exception {
		assertEquals(450, original.Add.add(430, 20));
	}

	@Test
	public void test77() throws Exception {
		assertEquals(1551, original.Add.add(916, 635));
	}

	@Test
	public void test78() throws Exception {
		assertEquals(1300, original.Add.add(301, 999));
	}

	@Test
	public void test79() throws Exception {
		assertEquals(920, original.Add.add(454, 466));
	}

	@Test
	public void test80() throws Exception {
		assertEquals(1164, original.Add.add(905, 259));
	}

	@Test
	public void test81() throws Exception {
		assertEquals(373, original.Add.add(168, 205));
	}

	@Test
	public void test82() throws Exception {
		assertEquals(1004, original.Add.add(570, 434));
	}

	@Test
	public void test83() throws Exception {
		assertEquals(1023, original.Add.add(64, 959));
	}

	@Test
	public void test84() throws Exception {
		assertEquals(1467, original.Add.add(957, 510));
	}

	@Test
	public void test85() throws Exception {
		assertEquals(1320, original.Add.add(722, 598));
	}

	@Test
	public void test86() throws Exception {
		assertEquals(996, original.Add.add(770, 226));
	}

	@Test
	public void test87() throws Exception {
		assertEquals(645, original.Add.add(579, 66));
	}

	@Test
	public void test88() throws Exception {
		assertEquals(791, original.Add.add(117, 674));
	}

	@Test
	public void test89() throws Exception {
		assertEquals(560, original.Add.add(530, 30));
	}

	@Test
	public void test90() throws Exception {
		assertEquals(1121, original.Add.add(776, 345));
	}

	@Test
	public void test91() throws Exception {
		assertEquals(716, original.Add.add(327, 389));
	}

	@Test
	public void test92() throws Exception {
		assertEquals(608, original.Add.add(596, 12));
	}

	@Test
	public void test93() throws Exception {
		assertEquals(1110, original.Add.add(599, 511));
	}

	@Test
	public void test94() throws Exception {
		assertEquals(1412, original.Add.add(936, 476));
	}

	@Test
	public void test95() throws Exception {
		assertEquals(475, original.Add.add(461, 14));
	}

	@Test
	public void test96() throws Exception {
		assertEquals(1123, original.Add.add(966, 157));
	}

	@Test
	public void test97() throws Exception {
		assertEquals(417, original.Add.add(326, 91));
	}

	@Test
	public void test98() throws Exception {
		assertEquals(847, original.Add.add(392, 455));
	}

	@Test
	public void test99() throws Exception {
		assertEquals(923, original.Add.add(446, 477));
	}

	@Test
	public void test100() throws Exception {
		assertEquals(1184, original.Add.add(324, 860));
	}

	@Test
	public void test101() throws Exception {
		assertEquals(1030, original.Add.add(945, 85));
	}

	@Test
	public void test102() throws Exception {
		assertEquals(1468, original.Add.add(886, 582));
	}

	@Test
	public void test103() throws Exception {
		assertEquals(1598, original.Add.add(886, 712));
	}

	@Test
	public void test104() throws Exception {
		assertEquals(1795, original.Add.add(842, 953));
	}

	
}