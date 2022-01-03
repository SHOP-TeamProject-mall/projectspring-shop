package com.example.impl;

import java.util.List;

import com.example.entity.Member;
import com.example.entity.MemberWish;
import com.example.service.MemberWishService;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberWishImpl implements MemberWishService {

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Override
    public List<Member> MemberWishSelect(String memberid) {
        try {
            return sqlSessionFactory.openSession().selectList("MemberWish.MemberWishSelect", memberid);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public int MemberWishDelete(Long memberwishno) {
        try {
            return sqlSessionFactory.openSession().delete("MemberWish.MemberWishDelete", memberwishno);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<MemberWish> MemberWish(Long memberWish) {
        try {
            return sqlSessionFactory.openSession().selectList("MemberWishList" , memberWish);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
