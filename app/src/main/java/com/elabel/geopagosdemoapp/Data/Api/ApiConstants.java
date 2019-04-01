package com.elabel.geopagosdemoapp.Data.Api;

public class ApiConstants {
    public static final String BASE_URL = "https://api.mercadopago.com/v1/";
    public static final String API_KEY = "TEST-8e3ae68a-b752-472f-9e69-fa0c73610695";
    //public static final String API_KEY_BANKS = "444a9ef5-8a6b-429f-abdf-587639155d88"; //usada porque la clave propia me daba error de key en Postman.
    public static final String PUBLIC_KEY_QUERY = "public_key";

    //Payment Methods
    public static final String PM = "payment_methods";

    //Card Issuers
    public static final String BANKS = PM + "/card_issuers";
    public static final String BANKS_CARDID_QUERY = "payment_method_id";


}
