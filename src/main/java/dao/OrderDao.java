package dao;

import model.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class OrderDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Order order) {
        if (order.getId() == null) {
            em.persist(order);
        } else {
            em.merge(order);
        }
    }

    public List<Order> findAll() {
        return em.createQuery("select o from Order o",
            Order.class).getResultList();
    }

    public void deleteAll() {
        em.createQuery("delete from Order o").executeUpdate();
    }

    public Order findOrderById(Long orderId) {
        TypedQuery<Order> query = em.createQuery(
                "select o from Order o where o.id = :orderId",
                Order.class);

        query.setParameter("orderId", orderId);

        return query.getSingleResult();
    }
}
