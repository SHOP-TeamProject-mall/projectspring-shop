package com.example.impl;

import java.util.List;

import com.example.entity.Member;
import com.example.service.MemberCartService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberCartImpl implements MemberCartService {

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Override
    public List<Member> MemberCartSelect(String memberid) {
        try {
            return sqlSessionFactory.openSession().selectList("MemberCart.MemberCartSelect", memberid);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Member> MemberCartDelete(Long membercartno) {
        try {
            return sqlSessionFactory.openSession().selectList("MemberCart.MemberCartDelete", membercartno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
