package com.elabel.geopagosdemoapp.Data.Api;

public class ApiConstants {
    public static final String BASE_URL = "https://api.mercadopago.com/";
    public static final String API_KEY = "TEST-8e3ae68a-b752-472f-9e69-fa0c73610695";
    public static final String API_KEY_AT = "TEST-589783640696767-032912-29f69d06902abbbd92650ce3ccd70dc8-73428035";
    //public static final String API_KEY_BANKS = "444a9ef5-8a6b-429f-abdf-587639155d88"; //usada porque la clave propia me daba error de key en Postman.
    public static final String PUBLIC_KEY_QUERY = "public_key";

    //Payment Methods
    public static final String PM = "v1/payment_methods";

    //Card Issuers
    public static final String BANKS = PM + "/card_issuers";
    public static final String BANKS_CARDID_QUERY = "payment_method_id";

    //Installments
    public static final String INSTALLMENTS = PM + "/installments";
    public static final String INSTALLMENTS_CARDID_QUERY = "payment_method_id";
    public static final String INSTALLMENTS_AMOUNT_QUERY = "amount";
    public static final String INSTALLMENTS_ISSUER_QUERY = "issuer.id";

    //PreferencesID
    public static final String PREFERENCESID = "checkout/preferences";
    public static final String PREFERENCESID_AT = "access_token";

}
