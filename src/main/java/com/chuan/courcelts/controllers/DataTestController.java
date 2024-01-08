package com.chuan.courcelts.controllers;

import com.chuan.courcelts.models.entities.Student;
import com.chuan.courcelts.repositories.dbcontexts.DbContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/test/random")
public class DataTestController {
    @Autowired
    DbContext dbContext;

    String[] firstnames = {"Uzumaki", "Uchiha", "Senju", "Hyuga", "Sarutobi", "Nara", "Akimichi", "Hatake"};
    String[] lastnames = {"Naruto", "Sasuke", "Sakura", "Kakashi", "Shikamaru",
    "Choji", "Ino", "Asuma", "Gai", "Hinata", "Kiba", "Shino", "Lee", "Neji", "Tenten",
    "Hashirama", "Tobirama", "Hizuzen", "Minato", "Tsunade", "Jiraiya", "Orochimaru"};
    String[] provinces = {"Hòa Bình", "Phú Thọ", "Sơn La", "Hà Nội", "Bắc Ninh", "Thanh Hóa", "Đà Nẵng",
    "Hồ Chí Minh", "Thừa Thiên Huế", "Cà Mau", "Kiên Giang"};
    String[] districts = {"A Lưới", "An Biên", "An Dương", "An Khê", "Phú Lộc", "Thuận An", "Khuê Trung", "Cẩm Lệ",
    "Hòa Cầm", "Từ Liêm", "Cầu Giấy"};
    String[] wards = {"Thuận An", "Vinh Giang", "Vinh Lộc", "Vinh Thanh", "Vinh Hiền", "Vinh Hải", "Cầu Hai",
    "Tân Mỹ", "Tân Lập", "Tân An", "Phú Diên"};

    @PostMapping("/student/{size}")
    void addStudent(@PathVariable int size){
        String firstname;
        String lastname;
        String image;
        String phone = "0363196", lastPhone;
        String email;
        LocalDate birthday;
        String province, district, ward;

        for(int i = 0; i < size; i++){
            firstname = firstnames[indexRandom(firstnames.length)];
            lastname = lastnames[indexRandom(lastnames.length)];
            lastPhone = "" + (i > 99 ? i : i > 9 ? "0"+i : "00"+i);
            image = "./image/avatar" + lastPhone;
            email = "email"+lastPhone + "@gmail.com";
            birthday = LocalDate.of(indexRandom(100) + 1900, indexRandom(12) + 1, indexRandom(28) + 1);
            province = provinces[indexRandom(provinces.length)];
            district = districts[indexRandom(districts.length)];
            ward = wards[indexRandom(wards.length)];
            String house = ""+indexRandom(1000);

            dbContext.studentRepository.save(new Student(0,
                    image,
                    firstname + " " + lastname,
                    birthday,
                    phone+lastPhone,
                    email,
                    province,
                    district,
                    ward,
                    house
                    ));
        }
    }

    int indexRandom(int size){
        return ((int)(Math.random() * 1000))%size;
    }

}
