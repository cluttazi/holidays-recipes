//package edu.neu.luttazi.model.persistance.dao;
//
//import java.util.List;
//
//import javax.persistence.EntityManagerFactory;
//
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import edu.neu.luttazi.model.Campaign;
//
//@Repository
//public class CampaignHibernateDAOImpl implements CampaignDAO {
//
//	private SessionFactory hibernateFactory;
//
//	@Autowired
//	public CampaignHibernateDAOImpl(EntityManagerFactory factory) {
//		if (factory.unwrap(SessionFactory.class) == null) {
//			throw new NullPointerException("factory is not a hibernate factory");
//		}
//		this.hibernateFactory = factory.unwrap(SessionFactory.class);
//	}
//
//	@Override
//	public Campaign save(Campaign campaign) {
//		return (Campaign) hibernateFactory.getCurrentSession().save(campaign);
//	}
//
//	@Override
//	public Campaign findByCampaignId(Long id) {
//		return (Campaign) hibernateFactory.getCurrentSession().load(Campaign.class, id);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Campaign> findAll() {
//		return (List<Campaign>) hibernateFactory.getCurrentSession().createQuery("from Campaign").list();
//	}
//}
