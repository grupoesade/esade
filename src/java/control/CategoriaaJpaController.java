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
import modelo.Categoriaa;

/**
 *
 * @author Paulino Francisco
 */
public class CategoriaaJpaController implements Serializable {

    public CategoriaaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Categoriaa categoriaa) {
        if (categoriaa.getProfessorList() == null) {
            categoriaa.setProfessorList(new ArrayList<Professor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Professor> attachedProfessorList = new ArrayList<Professor>();
            for (Professor professorListProfessorToAttach : categoriaa.getProfessorList()) {
                professorListProfessorToAttach = em.getReference(professorListProfessorToAttach.getClass(), professorListProfessorToAttach.getIdprofessor());
                attachedProfessorList.add(professorListProfessorToAttach);
            }
            categoriaa.setProfessorList(attachedProfessorList);
            em.persist(categoriaa);
            for (Professor professorListProfessor : categoriaa.getProfessorList()) {
                Categoriaa oldIdcategoriaOfProfessorListProfessor = professorListProfessor.getIdcategoria();
                professorListProfessor.setIdcategoria(categoriaa);
                professorListProfessor = em.merge(professorListProfessor);
                if (oldIdcategoriaOfProfessorListProfessor != null) {
                    oldIdcategoriaOfProfessorListProfessor.getProfessorList().remove(professorListProfessor);
                    oldIdcategoriaOfProfessorListProfessor = em.merge(oldIdcategoriaOfProfessorListProfessor);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Categoriaa categoriaa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoriaa persistentCategoriaa = em.find(Categoriaa.class, categoriaa.getIdcategoria());
            List<Professor> professorListOld = persistentCategoriaa.getProfessorList();
            List<Professor> professorListNew = categoriaa.getProfessorList();
            List<Professor> attachedProfessorListNew = new ArrayList<Professor>();
            for (Professor professorListNewProfessorToAttach : professorListNew) {
                professorListNewProfessorToAttach = em.getReference(professorListNewProfessorToAttach.getClass(), professorListNewProfessorToAttach.getIdprofessor());
                attachedProfessorListNew.add(professorListNewProfessorToAttach);
            }
            professorListNew = attachedProfessorListNew;
            categoriaa.setProfessorList(professorListNew);
            categoriaa = em.merge(categoriaa);
            for (Professor professorListOldProfessor : professorListOld) {
                if (!professorListNew.contains(professorListOldProfessor)) {
                    professorListOldProfessor.setIdcategoria(null);
                    professorListOldProfessor = em.merge(professorListOldProfessor);
                }
            }
            for (Professor professorListNewProfessor : professorListNew) {
                if (!professorListOld.contains(professorListNewProfessor)) {
                    Categoriaa oldIdcategoriaOfProfessorListNewProfessor = professorListNewProfessor.getIdcategoria();
                    professorListNewProfessor.setIdcategoria(categoriaa);
                    professorListNewProfessor = em.merge(professorListNewProfessor);
                    if (oldIdcategoriaOfProfessorListNewProfessor != null && !oldIdcategoriaOfProfessorListNewProfessor.equals(categoriaa)) {
                        oldIdcategoriaOfProfessorListNewProfessor.getProfessorList().remove(professorListNewProfessor);
                        oldIdcategoriaOfProfessorListNewProfessor = em.merge(oldIdcategoriaOfProfessorListNewProfessor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = categoriaa.getIdcategoria();
                if (findCategoriaa(id) == null) {
                    throw new NonexistentEntityException("The categoriaa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Short id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoriaa categoriaa;
            try {
                categoriaa = em.getReference(Categoriaa.class, id);
                categoriaa.getIdcategoria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoriaa with id " + id + " no longer exists.", enfe);
            }
            List<Professor> professorList = categoriaa.getProfessorList();
            for (Professor professorListProfessor : professorList) {
                professorListProfessor.setIdcategoria(null);
                professorListProfessor = em.merge(professorListProfessor);
            }
            em.remove(categoriaa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Categoriaa> findCategoriaaEntities() {
        return findCategoriaaEntities(true, -1, -1);
    }

    public List<Categoriaa> findCategoriaaEntities(int maxResults, int firstResult) {
        return findCategoriaaEntities(false, maxResults, firstResult);
    }

    private List<Categoriaa> findCategoriaaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categoriaa.class));
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

    public Categoriaa findCategoriaa(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categoriaa.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categoriaa> rt = cq.from(Categoriaa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
