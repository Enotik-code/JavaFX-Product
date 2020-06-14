package strings;

public class StringFile {
    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/product?useUnicode=yes&characterEncoding=UTF-8";
    public static final String USER = "root";
    public static final String PASSWORD = "1239087154a";


    public static final String SELECT_NAME_BY_EMAIL = "select user.name from user\n" +
            "where email =";
    public static final String SELECT_SURNAME_BY_EMAIL = "select user.surname from user\n" +
            "where email =";

    public static final String SELECT_ALL_FROM_USER = "select * from user";
    public static final String SELECT_ALL_FROM_PRODUCT = "select product.name , subcategory.Description, product.amount," +
            " product.dicsount, product.price\n" +
            "from product\n" +
            "join subcategory on product.idsubcategory = subcategory.id";
    public static final String SELECT_ALL_FROM_PRODUCT_BY_ID = "select product.name, subcategory.Description," +
            " product.amount, product.dicsount, product.price, country.Description\n" +
            "from product\n" +
            "join subcategory on product.idsubcategory = subcategory.id\n" +
            "join country on product.idmanufacturer = country.id\n" +
            "where product.name = '";

    public static final String SELECT_ALL_FROM_PRODUCT_ORDER_BY_PRICE_ASCENDING = "select product.name ," +
            " subcategory.Description, product.amount, product.dicsount, product.price\n" +
            "from product\n" +
            "join subcategory on product.idsubcategory = subcategory.id\n" +
            "order by product.price";
    public static final String SELECT_ALL_FROM_PRODUCT_ORDER_BY_PRICE_DESCENDING = "select product.name ," +
            " subcategory.Description, product.amount, product.dicsount, product.price\n" +
            "from product\n" +
            "join subcategory on product.idsubcategory = subcategory.id\n" +
            "order by product.price desc";
    public static final String SELECT_ALL_FROM_PRODUCT_BY_SUBCATEGORY = "select product.name , subcategory.Description," +
            " product.amount, product.dicsount, product.price\n" +
            "from product\n" +
            "join subcategory on product.idsubcategory = subcategory.id\n" +
            "where subcategory.Description = ";
    public static final String SELECT_ALL_FROM_PRODUCT_FIND_BY_NAME = "select product.name , subcategory.Description," +
            " product.amount, product.dicsount, product.price\n" +
            "from product\n" +
            "join subcategory on product.idsubcategory = subcategory.id\n" +
            "where product.name  like ";
    public static final String SELECT_ALL_FROM_CATEGORY = "select * from category";
    public static final String SELECT_ALL_FROM_SUBCATEGORY = "select * from subcategory";
    public static final String SELECT_ALL_FROM_SUBCATEGORY_BY_CATEGORY = "select category.Descrition," +
            " subcategory.Description\n" +
            "from subcategory\n" +
            "join category on subcategory.IdCategory = category.id\n" +
            "where category.Descrition =";
    public static final String SELECT_ALL_FROM_COUNTRY = "select * from country";

    public static final String INSERT_INTO_USER = "insert into user " +
            "(name, surname, patronymic, number, email, password," +
            " idPosition, idCountry, cardNumber, dateOfBirthday, dateOfCreated, dateOfModified)" +
            "values(?,?,?,?,?,?,?,?,?,?,?,?)";
    public static final String INSERT_INTO_PRODUCT = "insert into product " +
            "(name, idsubcategory, amount, start_price, dicsount, price, idmanufacturer)" +
            "values(?,?,?,?,?,?,?)";














    public static final String terms = "Last updated: (add date)\n" +
    "Please read these Terms and Conditions (Terms, Terms and Conditions) carefully before using\n"+
    "the http://www.mywebsite.com (change this) website and the My Mobile App (change this) mobile\n"+
    "application (the Service) operated by My Company (change this) (us, we, or our).\n"+
    "Your access to and use of the Service is conditioned on your acceptance of and compliance with\n"+
    "these Terms. These Terms apply to all visitors, users and others who access or use the Service.\n"+
    "By accessing or using the Service you agree to be bound by these Terms. If you disagree\n"+
    "with any part of the terms then you may not access the Service.\n"+
     "       Purchases\n"+
    "If you wish to purchase any product or service made available through the Service (Purchase),\n"+
    "you may be asked to supply certain information relevant to your Purchase including, without\n"+
    "limitation, your …\n"+
    "        Subscriptions\n"+
    "Some parts of the Service are billed on a subscription basis (Subscription(s)). You will be billed in\n"+
    "advance on a recurring ...\n"+
    "Content\n"+
    "Our Service allows you to post, link, store, share and otherwise make available certain information,\n"+
    "text, graphics, videos, or other material (Content). You are responsible for the …\n"+
    "Links To Other Web Sites\n"+
    "Our Service may contain links to third-party web sites or services that are not owned or controlled\n"+
    "by My Company (change this).\n"+
    "My Company (change this) has no control over, and assumes no responsibility for, the content,\n"+
    "privacy policies, or practices of any third party web sites or services. You further acknowledge and\n"+
    "agree that My Company (change this) shall not be responsible or liable, directly or indirectly, for any\n"+
    "damage or loss caused or alleged to be caused by or in connection with use of or reliance on any\n"+
    "such content, goods or services available on or through any such web sites or services.\n"+
    "Changes\n"+
    "We reserve the right, at our sole discretion, to modify or replace these Terms at any time. If a\n"+
    "revision is material we will try to provide at least 30 (change this) days' notice prior to any new terms\n"+
    "taking effect. What constitutes a material change will be determined at our sole discretion.\n"+
    "Contact Us\n"+
    "If you have any questions about these Terms, please contact us.\n";


}
