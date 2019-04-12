public class Person {
	public Person() {
		System.out.println("Constructor in Class Person");
	}

	@BeforeClass
	public void TestBefore_Apple() {
		System.out.println("Apple: @BeforeClass");
	}

	@BeforeClass
	public void TestBefore_Mango() {
		System.out.println("Mango: @BeforeClass");
	}

	@Before
	public void Test_before_A() {
		System.out.println("Before each test A");
	}

	@Before
	public void Test_before_D() {
		System.out.println("Before each test D");
	}

	@Test
	public void Test_Aaron() {
		System.out.println("Arron: @Test");
	}

	@Test
	public void Test_Austin() {
		System.out.println("Austin: @Test");
	}

	@Test
	public void Test_Bob() {
		System.out.println("Bob: @Test");
	}

	@Test
	public void Test_Michael() {
		System.out.println("Michael: @Test");
	}

	@After
	public void Test_after_C() {
		System.out.println("After each test C");
	}

	@After
	public void Test_after_S() {
		System.out.println("After each test S");
	}

	@AfterClass
	public void TestBefore_Zitman() {
		System.out.println("Zitman: @BeforeClass");
	}

	@AfterClass
	public void TestBefore_Victor() {
		System.out.println("Victor: @BeforeClass");
	}

}