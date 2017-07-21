/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.IllegalOrphanException;
import control.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Curso;
import modelo.Users;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Estudante;
import modelo.Matricula;

/**
 *
 * @author Paulino Francisco
 */
public class EstudanteJpaController implements Serializable {

    public EstudanteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estudante estudante) {
        if (estudante.getUsersList() == null) {
            estudante.setUsersList(new ArrayList<Users>());
        }
        if (estudante.getMatriculaList() == null) {
            estudante.setMatriculaList(new ArrayList<Matricula>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Curso idCurso = estudante.getIdCurso();
            if (idCurso != null) {
                idCurso = em.getReference(idCurso.getClass(), idCurso.getIdCurso());
                estudante.setIdCurso(idCurso);
            }
            List<Users> attachedUsersList = new ArrayList<Users>();
            for (Users usersListUsersToAttach : estudante.getUsersList()) {
                usersListUsersToAttach = em.getReference(usersListUsersToAttach.getClass(), usersListUsersToAttach.getUtilizador());
                attachedUsersList.add(usersListUsersToAttach);
            }
            estudante.setUsersList(attachedUsersList);
            List<Matricula> attachedMatriculaList = new ArrayList<Matricula>();
            for (Matricula matriculaListMatriculaToAttach : estudante.getMatriculaList()) {
                matriculaListMatriculaToAttach = em.getReference(matriculaListMatriculaToAttach.getClass(), matriculaListMatriculaToAttach.getMatriculaPK());
                attachedMatriculaList.add(matriculaListMatriculaToAttach);
            }
            estudante.setMatriculaList(attachedMatriculaList);
            em.persist(estudante);
            if (idCurso != null) {
                idCurso.getEstudanteList().add(estudante);
                idCurso = em.merge(idCurso);
            }
            for (Users usersListUsers : estudante.getUsersList()) {
                Estudante oldIdEstudanteOfUsersListUsers = usersListUsers.getIdEstudante();
                usersListUsers.setIdEstudante(estudante);
                usersListUsers = em.merge(usersListUsers);
                if (oldIdEstudanteOfUsersListUsers != null) {
                    oldIdEstudanteOfUsersListUsers.getUsersList().remove(usersListUsers);
                    oldIdEstudanteOfUsersListUsers = em.merge(oldIdEstudanteOfUsersListUsers);
                }
            }
            for (Matricula matriculaListMatricula : estudante.getMatriculaList()) {
                Estudante oldEstudanteOfMatriculaListMatricula = matriculaListMatricula.getEstudante();
                matriculaListMatricula.setEstudante(estudante);
                matriculaListMatricula = em.merge(matriculaListMatricula);
                if (oldEstudanteOfMatriculaListMatricula != null) {
                    oldEstudanteOfMatriculaListMatricula.getMatriculaList().remove(matriculaListMatricula);
                    oldEstudanteOfMatriculaListMatricula = em.merge(oldEstudanteOfMatriculaListMatricula);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estudante estudante) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudante persistentEstudante = em.find(Estudante.class, estudante.getIdEstudante());
            Curso idCursoOld = persistentEstudante.getIdCurso();
            Curso idCursoNew = estudante.getIdCurso();
            List<Users> usersListOld = persistentEstudante.getUsersList();
            List<Users> usersListNew = estudante.getUsersList();
            List<Matricula> matriculaListOld = persistentEstudante.getMatriculaList();
            List<Matricula> matriculaListNew = estudante.getMatriculaList();
            List<String> illegalOrphanMessages = null;
            for (Matricula matriculaListOldMatricula : matriculaListOld) {
                if (!matriculaListNew.contains(matriculaListOldMatricula)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Matricula " + matriculaListOldMatricula + " since its estudante field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idCursoNew != null) {
                idCursoNew = em.getReference(idCursoNew.getClass(), idCursoNew.getIdCurso());
                estudante.setIdCurso(idCursoNew);
            }
            List<Users> attachedUsersListNew = new ArrayList<Users>();
            for (Users usersListNewUsersToAttach : usersListNew) {
                usersListNewUsersToAttach = em.getReference(usersListNewUsersToAttach.getClass(), usersListNewUsersToAttach.getUtilizador());
                attachedUsersListNew.add(usersListNewUsersToAttach);
            }
            usersListNew = attachedUsersListNew;
            estudante.setUsersList(usersListNew);
            List<Matricula> attachedMatriculaListNew = new ArrayList<Matricula>();
            for (Matricula matriculaListNewMatriculaToAttach : matriculaListNew) {
                matriculaListNewMatriculaToAttach = em.getReference(matriculaListNewMatriculaToAttach.getClass(), matriculaListNewMatriculaToAttach.getMatriculaPK());
                attachedMatriculaListNew.add(matriculaListNewMatriculaToAttach);
            }
            matriculaListNew = attachedMatriculaListNew;
            estudante.setMatriculaList(matriculaListNew);
            estudante = em.merge(estudante);
            if (idCursoOld != null && !idCursoOld.equals(idCursoNew)) {
                idCursoOld.getEstudanteList().remove(estudante);
                idCursoOld = em.merge(idCursoOld);
            }
            if (idCursoNew != null && !idCursoNew.equals(idCursoOld)) {
                idCursoNew.getEstudanteList().add(estudante);
                idCursoNew = em.merge(idCursoNew);
            }
            for (Users usersListOldUsers : usersListOld) {
                if (!usersListNew.contains(usersListOldUsers)) {
                    usersListOldUsers.setIdEstudante(null);
                    usersListOldUsers = em.merge(usersListOldUsers);
                }
            }
            for (Users usersListNewUsers : usersListNew) {
                if (!usersListOld.contains(usersListNewUsers)) {
                    Estudante oldIdEstudanteOfUsersListNewUsers = usersListNewUsers.getIdEstudante();
                    usersListNewUsers.setIdEstudante(estudante);
                    usersListNewUsers = em.merge(usersListNewUsers);
                    if (oldIdEstudanteOfUsersListNewUsers != null && !oldIdEstudanteOfUsersListNewUsers.equals(estudante)) {
                        oldIdEstudanteOfUsersListNewUsers.getUsersList().remove(usersListNewUsers);
                        oldIdEstudanteOfUsersListNewUsers = em.merge(oldIdEstudanteOfUsersListNewUsers);
                    }
                }
            }
            for (Matricula matriculaListNewMatricula : matriculaListNew) {
                if (!matriculaListOld.contains(matriculaListNewMatricula)) {
                    Estudante oldEstudanteOfMatriculaListNewMatricula = matriculaListNewMatricula.getEstudante();
                    matriculaListNewMatricula.setEstudante(estudante);
                    matriculaListNewMatricula = em.merge(matriculaListNewMatricula);
                    if (oldEstudanteOfMatriculaListNewMatricula != null && !oldEstudanteOfMatriculaListNewMatricula.equals(estudante)) {
                        oldEstudanteOfMatriculaListNewMatricula.getMatriculaList().remove(matriculaListNewMatricula);
                        oldEstudanteOfMatriculaListNewMatricula = em.merge(oldEstudanteOfMatriculaListNewMatricula);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = estudante.getIdEstudante();
                if (findEstudante(id) == null) {
                    throw new NonexistentEntityException("The estudante with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudante estudante;
            try {
                estudante = em.getReference(Estudante.class, id);
                estudante.getIdEstudante();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estudante with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Matricula> matriculaListOrphanCheck = estudante.getMatriculaList();
            for (Matricula matriculaListOrphanCheckMatricula : matriculaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudante (" + estudante + ") cannot be destroyed since the Matricula " + matriculaListOrphanCheckMatricula + " in its matriculaList field has a non-nullable estudante field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Curso idCurso = estudante.getIdCurso();
            if (idCurso != null) {
                idCurso.getEstudanteList().remove(estudante);
                idCurso = em.merge(idCurso);
            }
            List<Users> usersList = estudante.getUsersList();
            for (Users usersListUsers : usersList) {
                usersListUsers.setIdEstudante(null);
                usersListUsers = em.merge(usersListUsers);
            }
            em.remove(estudante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estudante> findEstudanteEntities() {
        return findEstudanteEntities(true, -1, -1);
    }

    public List<Estudante> findEstudanteEntities(int maxResults, int firstResult) {
        return findEstudanteEntities(false, maxResults, firstResult);
    }

    private List<Estudante> findEstudanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estudante.class));
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

    public Estudante findEstudante(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estudante.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstudanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estudante> rt = cq.from(Estudante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
