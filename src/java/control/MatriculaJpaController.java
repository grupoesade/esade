/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.NonexistentEntityException;
import control.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Curso;
import modelo.Estudante;
import modelo.Matricula;
import modelo.MatriculaPK;

/**
 *
 * @author Paulino Francisco
 */
public class MatriculaJpaController implements Serializable {

    public MatriculaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Matricula matricula) throws PreexistingEntityException, Exception {
        if (matricula.getMatriculaPK() == null) {
            matricula.setMatriculaPK(new MatriculaPK());
        }
        matricula.getMatriculaPK().setIdEstudante(matricula.getEstudante().getIdEstudante());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Curso curso = matricula.getCurso();
            if (curso != null) {
                curso = em.getReference(curso.getClass(), curso.getIdCurso());
                matricula.setCurso(curso);
            }
            Estudante estudante = matricula.getEstudante();
            if (estudante != null) {
                estudante = em.getReference(estudante.getClass(), estudante.getIdEstudante());
                matricula.setEstudante(estudante);
            }
            em.persist(matricula);
            if (curso != null) {
                curso.getMatriculaList().add(matricula);
                curso = em.merge(curso);
            }
            if (estudante != null) {
                estudante.getMatriculaList().add(matricula);
                estudante = em.merge(estudante);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMatricula(matricula.getMatriculaPK()) != null) {
                throw new PreexistingEntityException("Matricula " + matricula + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Matricula matricula) throws NonexistentEntityException, Exception {
        matricula.getMatriculaPK().setIdEstudante(matricula.getEstudante().getIdEstudante());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Matricula persistentMatricula = em.find(Matricula.class, matricula.getMatriculaPK());
            Curso cursoOld = persistentMatricula.getCurso();
            Curso cursoNew = matricula.getCurso();
            Estudante estudanteOld = persistentMatricula.getEstudante();
            Estudante estudanteNew = matricula.getEstudante();
            if (cursoNew != null) {
                cursoNew = em.getReference(cursoNew.getClass(), cursoNew.getIdCurso());
                matricula.setCurso(cursoNew);
            }
            if (estudanteNew != null) {
                estudanteNew = em.getReference(estudanteNew.getClass(), estudanteNew.getIdEstudante());
                matricula.setEstudante(estudanteNew);
            }
            matricula = em.merge(matricula);
            if (cursoOld != null && !cursoOld.equals(cursoNew)) {
                cursoOld.getMatriculaList().remove(matricula);
                cursoOld = em.merge(cursoOld);
            }
            if (cursoNew != null && !cursoNew.equals(cursoOld)) {
                cursoNew.getMatriculaList().add(matricula);
                cursoNew = em.merge(cursoNew);
            }
            if (estudanteOld != null && !estudanteOld.equals(estudanteNew)) {
                estudanteOld.getMatriculaList().remove(matricula);
                estudanteOld = em.merge(estudanteOld);
            }
            if (estudanteNew != null && !estudanteNew.equals(estudanteOld)) {
                estudanteNew.getMatriculaList().add(matricula);
                estudanteNew = em.merge(estudanteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MatriculaPK id = matricula.getMatriculaPK();
                if (findMatricula(id) == null) {
                    throw new NonexistentEntityException("The matricula with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MatriculaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Matricula matricula;
            try {
                matricula = em.getReference(Matricula.class, id);
                matricula.getMatriculaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The matricula with id " + id + " no longer exists.", enfe);
            }
            Curso curso = matricula.getCurso();
            if (curso != null) {
                curso.getMatriculaList().remove(matricula);
                curso = em.merge(curso);
            }
            Estudante estudante = matricula.getEstudante();
            if (estudante != null) {
                estudante.getMatriculaList().remove(matricula);
                estudante = em.merge(estudante);
            }
            em.remove(matricula);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Matricula> findMatriculaEntities() {
        return findMatriculaEntities(true, -1, -1);
    }

    public List<Matricula> findMatriculaEntities(int maxResults, int firstResult) {
        return findMatriculaEntities(false, maxResults, firstResult);
    }

    private List<Matricula> findMatriculaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Matricula.class));
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

    public Matricula findMatricula(MatriculaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Matricula.class, id);
        } finally {
            em.close();
        }
    }

    public int getMatriculaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Matricula> rt = cq.from(Matricula.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
