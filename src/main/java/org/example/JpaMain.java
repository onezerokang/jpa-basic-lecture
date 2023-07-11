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
            Member member = em.find(Member.class, 150L);
            member.setName("ZZZZ");
            // em.persist(member); JPA를 잘 모르면 변경 사항을 저장해야 하는 것으로 생각하지만
            // JPA는 자바 컬렉션처럼 db를 사용하는 것. 변경하면 변경된다.

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}
