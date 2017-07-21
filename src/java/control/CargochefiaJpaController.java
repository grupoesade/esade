/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Professor;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Cargochefia;

/**
 *
 * @author Paulino Francisco
 */
public class CargochefiaJpaController implements Serializable {

    public CargochefiaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cargochefia cargochefia) {
        if (cargochefia.getProfessorList() == null) {
            cargochefia.setProfessorList(new ArrayList<Professor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Professor> attachedProfessorList = new ArrayList<Professor>();
            for (Professor professorListProfessorToAttach : cargochefia.getProfessorList()) {
                professorListProfessorToAttach = em.getReference(professorListProfessorToAttach.getClass(), professorListProfessorToAttach.getIdprofessor());
                attachedProfessorList.add(professorListProfessorToAttach);
            }
            cargochefia.setProfessorList(attachedProfessorList);
            em.persist(cargochefia);
            for (Professor professorListProfessor : cargochefia.getProfessorList()) {
                Cargochefia oldIdcargochefiaOfProfessorListProfessor = professorListProfessor.getIdcargochefia();
                professorListProfessor.setIdcargochefia(cargochefia);
                professorListProfessor = em.merge(professorListProfessor);
                if (oldIdcargochefiaOfProfessorListProfessor != null) {
                    oldIdcargochefiaOfProfessorListProfessor.getProfessorList().remove(professorListProfessor);
                    oldIdcargochefiaOfProfessorListProfessor = em.merge(oldIdcargochefiaOfProfessorListProfessor);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cargochefia cargochefia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cargochefia persistentCargochefia = em.find(Cargochefia.class, cargochefia.getIdcargochefia());
            List<Professor> professorListOld = persistentCargochefia.getProfessorList();
            List<Professor> professorListNew = cargochefia.getProfessorList();
            List<Professor> attachedProfessorListNew = new ArrayList<Professor>();
            for (Professor professorListNewProfessorToAttach : professorListNew) {
                professorListNewProfessorToAttach = em.getReference(professorListNewProfessorToAttach.getClass(), professorListNewProfessorToAttach.getIdprofessor());
                attachedProfessorListNew.add(professorListNewProfessorToAttach);
            }
            professorListNew = attachedProfessorListNew;
            cargochefia.setProfessorList(professorListNew);
            cargochefia = em.merge(cargochefia);
            for (Professor professorListOldProfessor : professorListOld) {
                if (!professorListNew.contains(professorListOldProfessor)) {
                    professorListOldProfessor.setIdcargochefia(null);
                    professorListOldProfessor = em.merge(professorListOldProfessor);
                }
            }
            for (Professor professorListNewProfessor : professorListNew) {
                if (!professorListOld.contains(professorListNewProfessor)) {
                    Cargochefia oldIdcargochefiaOfProfessorListNewProfessor = professorListNewProfessor.getIdcargochefia();
                    professorListNewProfessor.setIdcargochefia(cargochefia);
                    professorListNewProfessor = em.merge(professorListNewProfessor);
                    if (oldIdcargochefiaOfProfessorListNewProfessor != null && !oldIdcargochefiaOfProfessorListNewProfessor.equals(cargochefia)) {
                        oldIdcargochefiaOfProfessorListNewProfessor.getProfessorList().remove(professorListNewProfessor);
                        oldIdcargochefiaOfProfessorListNewProfessor = em.merge(oldIdcargochefiaOfProfessorListNewProfessor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cargochefia.getIdcargochefia();
                if (findCargochefia(id) == null) {
                    throw new NonexistentEntityException("The cargochefia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cargochefia cargochefia;
            try {
                cargochefia = em.getReference(Cargochefia.class, id);
                cargochefia.getIdcargochefia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cargochefia with id " + id + " no longer exists.", enfe);
            }
            List<Professor> professorList = cargochefia.getProfessorList();
            for (Professor professorListProfessor : professorList) {
                professorListProfessor.setIdcargochefia(null);
                professorListProfessor = em.merge(professorListProfessor);
            }
            em.remove(cargochefia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cargochefia> findCargochefiaEntities() {
        return findCargochefiaEntities(true, -1, -1);
    }

    public List<Cargochefia> findCargochefiaEntities(int maxResults, int firstResult) {
        return findCargochefiaEntities(false, maxResults, firstResult);
    }

    private List<Cargochefia> findCargochefiaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cargochefia.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Cargochefia findCargochefia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cargochefia.class, id);
        } finally {
            em.close();
        }
    }

    public int getCargochefiaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cargochefia> rt = cq.from(Cargochefia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
