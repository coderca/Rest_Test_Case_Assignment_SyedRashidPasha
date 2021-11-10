import io.restassured.RestAssured;
import java.util.Properties;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;
import static org.testng.TestRunner.PriorityWeight.priority;

public class api {

    public String access_token;
    String payload = "{\n" +
            "\"username\" : \"upskills_admin\",\n" +
            "\"password\" : \"Talent4$$\"\n" +
            "}";

    String payload1 = "{\n" +
            "  \"firstname\": \"Sam\",\n" +
            "  \"lastname\": \"Stark\",\n" +
            "  \"email\": \"Sam@gmail.him\",\n" +
            "  \"password\": \"password\",\n" +
            "  \"confirm\": \"password\",\n" +
            "  \"telephone\": \"1-541-100-3010\",\n" +
            "  \"fax\": \"1-541-100-3010\",\n" +
            "  \"newsletter\": \"1\",\n" +
            "  \"status\": \"1\",\n" +
            "  \"approved\": \"1\",\n" +
            "  \"safe\": \"1\",\n" +
            "  \"customer_group_id\": \"1\",\n" +
            "  \"custom_field\": {\n" +
            "    \"1\": \"1985-02-01\",\n" +
            "    \"2\": \"2\"\n" +
            "  },\n" +
            "  \"address\": [\n" +
            "    {\n" +
            "      \"firstname\": \"Sam\",\n" +
            "      \"lastname\": \"Stark\",\n" +
            "      \"company\": \"Stark Industries LTD.\",\n" +
            "      \"city\": \"Paris\",\n" +
            "      \"address_1\": \"Elm street\",\n" +
            "      \"country_id\": \"81\",\n" +
            "      \"postcode\": \"5000\",\n" +
            "      \"zone_id\": \"1255\",\n" +
            "      \"address_2\": \"Long street 77\",\n" +
            "      \"address_id\": \"11\",\n" +
            "      \"default\": \"1\",\n" +
            "      \"custom_field\": {\n" +
            "        \"3\": \"https://rest-api.upskills.in\"\n" +
            "      }\n" +
            "    }\n" +
            "  ],\n" +
            "  \"affiliate\": {\n" +
            "    \"company\": \"Stark Ind\",\n" +
            "    \"website\": \"http://Stark.com\",\n" +
            "    \"tracking\": \"64377\",\n" +
            "    \"commission\": \"3\",\n" +
            "    \"tax\": \"99995555\",\n" +
            "    \"cheque\": \"Sam\",\n" +
            "    \"paypal\": \"Sam@gmail.com\",\n" +
            "    \"bank_name\": \"Bank Name\",\n" +
            "    \"bank_branch_number\": \"ABA/BSB number (Branch Number)\",\n" +
            "    \"bank_swift_code\": \"SWIFT Code\",\n" +
            "    \"bank_account_name\": \"Account Name\",\n" +
            "    \"bank_account_number\": \"Account Number\",\n" +
            "    \"status\": \"1\",\n" +
            "    \"payment\": \"Cash\",\n" +
            "    \"custom_field\": {\n" +
            "      \"1\": \"custom field\"\n" +
            "    }\n" +
            "  }\n" +
            "}";

    //TC_001 (EVERYONE)
    @Test (priority = 1)
    public void token(){
        Response responsePost = RestAssured.given().header("Authorization","Basic dXBza2lsbHNfcmVzdF9hZG1pbl9vYXV0aF9jbGllbnQ6dXBza2lsbHNfcmVzdF9hZG1pbl9vYXV0aF9zZWNyZXQ=")
                .given().header("Accept","application/json")
                .given().param("grant_type","client_credentials")
                .log().all()
                .post("http://rest-api.upskills.in/api/rest_admin/oauth2/token/client_credentials")
                .then().assertThat().statusCode(200)
                .extract()
                .response();
        //System.out.println(responsePost.body().asString().substring(Integer.parseInt("260")));
        //System.out.println(responsePost.body().asString().substring(Integer.parseInt("276"), Integer.parseInt("316")));
        access_token = responsePost.body().asString().substring(Integer.parseInt("276"), Integer.parseInt("316"));
        System.out.println("the status code is " +responsePost.getStatusCode());
        System.out.println("token is:" +access_token);


    }

    //TC_002 (EVERYONE)
    @Test (priority = 2)
    public void login1(){
        Response reslog = given().header("Authorization","Bearer "+access_token)
                .given().header("Content-Type","application/json")
                .given().body(payload).post("http://rest-api.upskills.in/api/rest_admin/login");
        System.out.println("the status code is " +reslog.getStatusCode());
        System.out.println("token is " +access_token);
        reslog.prettyPrint();
    }

    //TC_002 (EVERYONE)
    @Test (priority = 3)
    public void user(){
        Response responseGet = given().header("Authorization","Bearer "+access_token)
                .given().get("http://rest-api.upskills.in/api/rest_admin/user");
        System.out.println("the status code is " +responseGet.getStatusCode());
        System.out.println(responseGet.getBody().asString());
    }

    //TC_005 (SYED RASHID PASHA)
    @Test (priority = 4)
    public void addcustomers(){
        Response resadd = given().header("Authorization","Bearer "+access_token)
                .given().header("Content-Type","application/json")
                .given().body(payload1).post("http://rest-api.upskills.in/api/rest_admin/customers");
        System.out.println("the status code is " +resadd.getStatusCode());
        System.out.println(resadd.getBody().asString());
    }

    //TC_005 (SYED RASHID PASHA)
    @Test (priority = 5)
    public void getcus(){
        Response resget = given().header("Authorization","Bearer "+access_token)
                .queryParam("added_from","2019-11-20")
                .given().get("http://rest-api.upskills.in/api/rest_admin/customers/added_from/");
        System.out.println("the status code is " +resget.getStatusCode());
    }

    //TC_006 (SYED RASHID PASHA)
    @Test (priority = 6)
    public void getcusfromto(){
        Response res = given().header("Authorization","Bearer "+access_token)
                .pathParams("added_from","2019-11-20","added_to","2019-11-30")
                .given().get("http://rest-api.upskills.in/api/rest_admin/customers/added_from/{added_from}/added_to/{added_to}");
        System.out.println("the status code is " +res.getStatusCode());
    }

    //TC_002 (EVERYONE)
    @Test (priority = 7)
    public void logout(){
        Response resout = given().header("Authorization","Bearer "+access_token)
                .post("http://rest-api.upskills.in/api/rest_admin/logout");
        System.out.println("the status code is " +resout.getStatusCode());
    }

}
