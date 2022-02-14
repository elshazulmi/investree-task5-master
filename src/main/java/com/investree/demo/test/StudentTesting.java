package com.investree.demo.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentTesting {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void saveStudent() throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "*/*");
        headers.set("Content-Type", "application/json");
        String bodyTesting = "{\n" +
                "        \"firstName\" : \"Mini\",\n" +
                "        \"lastName\" : \"Mina\",\n" +
                "        \"angkatan\" : 2014,\n" +
                "        \"jurusan\" : \"Fisika\",\n" +
                "        \"fakultas\" : \"MIPA\",\n" +
                "        \"studentDetail\" : {\n" +
                "            \"nim\" : \"154747475\",\n" +
                "            \"alamatStudent\" : \"Jakarta\"   \n" +
                "        }\n" +
                "    }";
        System.out.println(bodyTesting);
        HttpEntity<String> entity = new HttpEntity<String>(bodyTesting, headers);
        ResponseEntity<String> exchange = testRestTemplate.exchange
                ("http://localhost:8081/api/student/simpan", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        System.out.println("response=" + exchange.getBody());
    }

    @Test
    public void updateStudent() throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "*/*");
        headers.set("Content-Type", "application/json");
        String bodyTesting = "{\n" +
                "        \"firstName\" : \"Mini\",\n" +
                "        \"lastName\" : \"Mina\",\n" +
                "        \"angkatan\" : 2014,\n" +
                "        \"jurusan\" : \"Fisika\",\n" +
                "        \"fakultas\" : \"MIPA\",\n" +
                "        \"studentDetail\" : {\n" +
                "            \"nim\" : \"154747475\",\n" +
                "            \"alamatStudent\" : \"Jakarta\"   \n" +
                "        }\n" +
                "    }";
        HttpEntity<String> entity = new HttpEntity<String>(bodyTesting, headers);
        Long id = 1L;
        ResponseEntity<String> exchange = testRestTemplate.exchange
                ("http://localhost:8081/api/student/update/" + id, HttpMethod.PUT, entity, String.class);
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        System.out.println("response=" + exchange.getBody());
    }

    @Test
    public void deleteStudent() throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "*/*");
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        Long id = 1L;
        ResponseEntity<String> exchange = testRestTemplate.exchange
                ("http://localhost:8081/api/student/delete/" + id, HttpMethod.DELETE, entity, String.class);

        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        System.out.println("response =" + exchange.getBody());

    }

    @Test
    public void listStudent() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "*/*");
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> exchange = testRestTemplate.exchange
                ("http://localhost:8081/api/student/listStudent", HttpMethod.GET, entity, String.class);

        //test data => respon nya valid atau tidak
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        System.out.println("response =" + exchange.getBody());
    }

}
