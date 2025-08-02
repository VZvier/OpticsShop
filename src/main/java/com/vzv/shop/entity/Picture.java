package com.vzv.shop.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PICTURES")
public class Picture {

    @Id
    @Column(name = "Id")
    private String id;
    private String name;
    private String type;
    private byte[] content;

    public Picture(String id, String name, String type, byte[] content) {
        this.id = id.trim();
        this.name = name.trim();
        this.type = type.trim();
        this.content = content;
    }

    @JsonCreator
    public Picture(Map<String, String> params){
        this.id = params.get("id");
        this.name = params.get("name");
        this.type = params.get("type");
        this.content = params.get("content").getBytes(StandardCharsets.UTF_8);
    }

    public String toString(){
        return "Picture{ Id: " +  this.id.trim()
                + ", Type: " + this.type.trim() + ", Name: " + this.name.trim() + "}";
    }


    public String getBase64Encoder(){
        return Base64.getEncoder().encodeToString(this.content);
    }
    public void getBase64Decoder(String content){
        this.content = Base64.getDecoder().decode(content);
    }
}