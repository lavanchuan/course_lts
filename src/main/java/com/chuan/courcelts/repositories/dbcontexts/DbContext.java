package com.chuan.courcelts.repositories.dbcontexts;

import com.chuan.courcelts.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DbContext {
    @Autowired
    public IAccountRepository accountRepository;
    @Autowired
    public IStudentRepository studentRepository;
    @Autowired
    public IArticleRepository articleRepository;
    @Autowired
    public IArticleTypeRepository articleTypeRepository;
    @Autowired
    public ICourceRepository courceRepository;
    @Autowired
    public ICourceTypeRepository courceTypeRepository;
    @Autowired
    public IStatusStudyRepository statusRepository;
    @Autowired
    public IRegisterRepository registerRepository;
    @Autowired
    public IThemeRepository themeRepository;
    @Autowired
    public IAuthorityRepository authorityRepository;
}
