/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlpg;

import conexao.Jpa;
import control.DisciplinaJpaController;
import control.ParametroautoavaliacaoJpaController;
import control.ParametrodocenteestudanteJpaController;
import control.ProfessorJpaController;
import control.QuestaodocenteestudanteJpaController;
import java.util.ArrayList;
import java.util.List;
import modelo.Disciplina;
import modelo.Parametroautoavaliacao;
import modelo.Parametrodocenteestudante;
import modelo.Professor;
import modelo.Questaodocenteestudante;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.lang.Strings;
import org.zkoss.xel.VariableResolver;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Paulino Francisco
 */
public class parametroestudante extends SelectorComposer<Component> {

    private List<Questaodocenteestudante> listaquestaoList;
    private List<Parametrodocenteestudante> listaParametrodocenteestudantes;

    @Wire
    private Parametroautoavaliacao parametroautoavaliacao = new Parametroautoavaliacao();
    private ParametroautoavaliacaoJpaController pjc;

    @Wire
    private Window cadpauto;
////Machauma/////////////////////////////////////////////////////////////
    @Wire
    Button up, guardar, add;
    @Wire
    Listbox lista_autores, lista_autores2, lista_par, comboquest;
    @Wire
    Div div_lista_autores;
    @Wire
    Textbox autor_search;
    @Wire
    Textbox codigo;
    @Wire
    Textbox descricao;
    ListModelList<Parametroautoavaliacao> lista2 = new ListModelList<Parametroautoavaliacao>();
    private List<Professor> listadocente;
    private List<Disciplina> listadisc;
 private ArrayList<Disciplina> listadisc1=new ArrayList<Disciplina>();
 
    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        listadocente = new ProfessorJpaController(new Jpa().getEmf()).findProfessorEntities();
        listadisc = new DisciplinaJpaController(new Jpa().getEmf()).findDisciplinaEntities();
        for (Disciplina disc : listadisc){
            if(disc.getSemestre()==1 && disc.getNivel()==4 && disc.getCurso().getIdCurso()==1){
              listadisc1.add(disc);
            }
        }
       listadisc=listadisc1; 
    }

    @Listen("onClick = #addparameto")
    public void adicionarParametro() {
        String codi = codigo.getValue();
        if (Strings.isBlank(codi)) {
            Clients.showNotification("Informe o codigo do parametro!", "warning", null, null, 3000);
        } else if (isCodigo(codi)) {
            Clients.showNotification("O codigo ja existe!", "warning", null, null, 3000);
        } else {
            Parametrodocenteestudante pau = new Parametrodocenteestudante();

            pau.setCodigo(codigo.getText());
            pau.setDescricao(descricao.getText());
            pau.setIdquestaodocenteestudante((Questaodocenteestudante) comboquest.getSelectedItem().getValue());

            try {
                new ParametrodocenteestudanteJpaController(new Jpa().getEmf()).create(pau);
                Clients.showNotification("parameto adicionado com sucesso!", "info", null, null, 3000);
                codigo.setValue("");
                descricao.setValue("");
            } catch (Exception e) {
                Clients.showNotification("erro ao guardar", "warning", null, null, 3000);
            }

        }

    }

    public boolean isCodigo(String codi) {
        listaParametrodocenteestudantes = new ParametrodocenteestudanteJpaController(new Jpa().getEmf()).findParametrodocenteestudanteEntities();
        for (Parametrodocenteestudante a : listaParametrodocenteestudantes) {
            if (a.getCodigo() != null) {
                if (a.getCodigo().equalsIgnoreCase(codi)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Listen("onCreate = #combo_curso; onCreate = #comboquest; onCreate = #combo_area")
    public void doCriarListas() {

        ListModelList<Questaodocenteestudante> listaquest = new ListModelList<Questaodocenteestudante>(new QuestaodocenteestudanteJpaController(new Jpa().getEmf()).findQuestaodocenteestudanteEntities());
        comboquest.setModel(listaquest);

    }

    public List<Disciplina> getListadisc() {
        return listadisc;
    }

    public void setListadisc(List<Disciplina> listadisc) {
        this.listadisc = listadisc;
    }

    public List<Questaodocenteestudante> getListaquestaoList() {
        return listaquestaoList;
    }

    public void setListaquestaoList(List<Questaodocenteestudante> listaquestaoList) {
        this.listaquestaoList = listaquestaoList;
    }

    public List<Parametrodocenteestudante> getListaParametrodocenteestudantes() {
        return listaParametrodocenteestudantes;
    }

    public void setListaParametrodocenteestudantes(List<Parametrodocenteestudante> listaParametrodocenteestudantes) {
        this.listaParametrodocenteestudantes = listaParametrodocenteestudantes;
    }

    public Parametroautoavaliacao getParametroautoavaliacao() {
        return parametroautoavaliacao;
    }

    public void setParametroautoavaliacao(Parametroautoavaliacao parametroautoavaliacao) {
        this.parametroautoavaliacao = parametroautoavaliacao;
    }

    public ParametroautoavaliacaoJpaController getPjc() {
        return pjc;
    }

    public void setPjc(ParametroautoavaliacaoJpaController pjc) {
        this.pjc = pjc;
    }

    public Window getCadpauto() {
        return cadpauto;
    }

    public void setCadpauto(Window cadpauto) {
        this.cadpauto = cadpauto;
    }

    public Button getUp() {
        return up;
    }

    public void setUp(Button up) {
        this.up = up;
    }

    public Button getGuardar() {
        return guardar;
    }

    public void setGuardar(Button guardar) {
        this.guardar = guardar;
    }

    public Button getAdd() {
        return add;
    }

    public void setAdd(Button add) {
        this.add = add;
    }

    public Listbox getLista_autores() {
        return lista_autores;
    }

    public void setLista_autores(Listbox lista_autores) {
        this.lista_autores = lista_autores;
    }

    public Listbox getLista_autores2() {
        return lista_autores2;
    }

    public void setLista_autores2(Listbox lista_autores2) {
        this.lista_autores2 = lista_autores2;
    }

    public Listbox getLista_par() {
        return lista_par;
    }

    public void setLista_par(Listbox lista_par) {
        this.lista_par = lista_par;
    }

    public Listbox getComboquest() {
        return comboquest;
    }

    public void setComboquest(Listbox comboquest) {
        this.comboquest = comboquest;
    }

    public Div getDiv_lista_autores() {
        return div_lista_autores;
    }

    public void setDiv_lista_autores(Div div_lista_autores) {
        this.div_lista_autores = div_lista_autores;
    }

    public Textbox getAutor_search() {
        return autor_search;
    }

    public void setAutor_search(Textbox autor_search) {
        this.autor_search = autor_search;
    }

    public Textbox getCodigo() {
        return codigo;
    }

    public void setCodigo(Textbox codigo) {
        this.codigo = codigo;
    }

    public Textbox getDescricao() {
        return descricao;
    }

    public void setDescricao(Textbox descricao) {
        this.descricao = descricao;
    }

    public ListModelList<Parametroautoavaliacao> getLista2() {
        return lista2;
    }

    public void setLista2(ListModelList<Parametroautoavaliacao> lista2) {
        this.lista2 = lista2;
    }

    public List<Professor> getListadocente() {
        return listadocente;
    }

    public void setListadocente(List<Professor> listadocente) {
        this.listadocente = listadocente;
    }

}
