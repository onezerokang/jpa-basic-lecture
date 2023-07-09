package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        // 디비 커넥션을 얻고, 쿼리를 날릴 때마다 entity manager를 만들어줘야 한다.
        // entityManger는 내부적으로 데이터베이스 커넥션을 물고 동작하기 때문에 사용을 다 하고 나면 닫아줘야 한다.
        // em을 자바 컬렉션이라고 생각하면 편하다.

        // jpa는 트랜잭션이라는 단위가 중요하다. jpa에서 데이터를 변경하는 모든 작업은 트랜잭션 안에서 해야 한다.
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // jpa는 테이블을 대상으로 쿼리하지 않는다.
            // 멤버 객체를 가져오는 jpql
            // 객체를 대상으로 하는 객체 지향 쿼리 -> db에 맞게 번역을 해준다.
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(8)
                    .getResultList();

            for (Member member : result) {
                System.out.println("member = " + member.getName());
            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}
