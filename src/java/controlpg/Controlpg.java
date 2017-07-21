/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlpg;

import conexao.Jpa;
import control.CaracteristiquestaosuperiorJpaController;
import control.EstudanteJpaController;
import control.CargochefiaJpaController;
import control.GrupoJpaController;
import control.ParametroautoavaliacaoJpaController;
import control.ParametrodocenteestudanteJpaController;
import modelo.Autoavaliacao;
import control.ProfessorJpaController;
import control.QuestaoautoavaliacaoJpaController;
import control.QuestaodocenteestudanteJpaController;
import control.QuestaodocentesuperiorJpaController;
import control.RegimeJpaController;
import control.UsersJpaController;
import control.AutoavaliacaoJpaController;
import control.CategoriaaJpaController;
import control.DepartamentoJpaController;
import control.PontuacaodocenteestudanteJpaController;
import control.exceptions.NonexistentEntityException;
import java.sql.Date;

import java.util.List;
import modelo.Caracteristiquestaosuperior;
import modelo.Categoriaa;
import modelo.Departamento;
import modelo.Estudante;
import modelo.Cargochefia;
import modelo.Grupo;
import modelo.Parametroautoavaliacao;
import modelo.Parametrodocenteestudante;
import modelo.Pontuacaodocenteestudante;
import modelo.Professor;
import modelo.Questaoautoavaliacao;
import modelo.Questaodocenteestudante;
import modelo.Questaodocentesuperior;
import modelo.Regime;
import modelo.Users;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.lang.Strings;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author ali_sualei
 */
public class Controlpg extends GenericForwardComposer {

    private String pesqNome;
// declaracao das listas
    private List<Professor> listadocente;
    private List<Parametrodocenteestudante> listaparametro;
    private List<Questaodocentesuperior> listaparametrosuper;
    private List<Parametroautoavaliacao> listaParamAutoAval;
    private List<Questaoautoavaliacao> listaQuestAutoAval;
    private List<Parametrodocenteestudante> listaParametroestuda;
    private List<Questaodocenteestudante> listaQuestEstuda;
    private List<Caracteristiquestaosuperior> listacaracteristic;
    private List<Questaodocentesuperior> listaquestsuper;
    private List<Grupo> listaGrupo;
    private List<Estudante> listaEstudante;
    private List<Users> listaUser;
    private List<Regime> listaregime;
    private List<Cargochefia> listafunc;
    private List<Autoavaliacao> listaAuto;
    private List<Departamento> listadep;
    private List<Categoriaa> listacate;
    private List<Professor> prof1;
//Onde:
//Po – Pontos obtidos;
//QA – Quantidade actual;
//PP – Pontuação padrão;
//QP – Quantidade padrão.
//	Se Po ≤ PP Bônus =0
//	Se Po >PP Bônus =PP/2

    private Textbox quantida, observacaoAuto;
    private Window self; //embedded object, the supervised window "mywin"
    private Page page; //the ZK zuml page
    private Label mylabel;
   public float pt = 0;
    @Wire
    org.zkoss.zul.Textbox codigo;
    @Wire
    Div autoavaliacao;
//declaracao da windows
    @Wire
    Datebox ano;
    @Wire
    private Window winparametro, winquestao, winparametroestudante, winquestaoestu, wincaracterist, winsuperior, winUser, wingrupo, winestuda, winprofe;

    // metodo init serve para listar
    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        listadocente = new ProfessorJpaController(new Jpa().getEmf()).findProfessorEntities();
        listaparametro = new ParametrodocenteestudanteJpaController(new Jpa().getEmf()).findParametrodocenteestudanteEntities();
        listaparametrosuper = new QuestaodocentesuperiorJpaController(new Jpa().getEmf()).findQuestaodocentesuperiorEntities();
        listaParamAutoAval = new ParametroautoavaliacaoJpaController(new Jpa().getEmf()).findParametroautoavaliacaoEntities();
        listaQuestAutoAval = new QuestaoautoavaliacaoJpaController(new Jpa().getEmf()).findQuestaoautoavaliacaoEntities();
        listaParametroestuda = new ParametrodocenteestudanteJpaController(new Jpa().getEmf()).findParametrodocenteestudanteEntities();
        listaQuestEstuda = new QuestaodocenteestudanteJpaController(new Jpa().getEmf()).findQuestaodocenteestudanteEntities();
        listacaracteristic = new CaracteristiquestaosuperiorJpaController(new Jpa().getEmf()).findCaracteristiquestaosuperiorEntities();
        listaquestsuper = new QuestaodocentesuperiorJpaController(new Jpa().getEmf()).findQuestaodocentesuperiorEntities();
        listaUser = new UsersJpaController(new Jpa().getEmf()).findUsersEntities();
        listaGrupo = new GrupoJpaController(new Jpa().getEmf()).findGrupoEntities();
        listaEstudante = new EstudanteJpaController(new Jpa().getEmf()).findEstudanteEntities();
        listaregime = new RegimeJpaController(new Jpa().getEmf()).findRegimeEntities();
        listafunc = new CargochefiaJpaController(new Jpa().getEmf()).findCargochefiaEntities();
        listaAuto = new AutoavaliacaoJpaController(new Jpa().getEmf()).findAutoavaliacaoEntities();
        listacate = new CategoriaaJpaController(new Jpa().getEmf()).findCategoriaaEntities();
        listadep = new DepartamentoJpaController(new Jpa().getEmf()).findDepartamentoEntities();
        prof1 = new ProfessorJpaController(new Jpa().getEmf()).findProfessorEntities();

    }

    
    public void onGuardar(final ForwardEvent evt)  {
        
        Controlpg cpg = new Controlpg();
        Textbox txt = (Textbox) evt.getOrigin().getTarget();
        Listitem litem = (Listitem) txt.getParent().getParent();
        Parametroautoavaliacao pat = (Parametroautoavaliacao) litem.getValue();
        
        pat.setQuantidade(Short.parseShort(txt.getText()));
        new ParametroautoavaliacaoJpaController(new Jpa().getEmf()).create(pat);

        float po = (float) (Integer.parseInt(txt.getText()) * 7) / 4;
        pt = pt + po;
        Messagebox.show(pt + "soma");

        
   
       
    }
 ////////////////////////////////////////////////////////////////////////////////////////////////////////

 @Listen("onClick = #addobser")
    public void adicionarObservacao() throws Exception {
        String codi = observacaoAuto.getValue();
        java.util.Date tempo=ano.getValue();
        if (Strings.isBlank(codi)) {
            Clients.showNotification("Nao foi posivel auto avaliar-se porque provavelmente tem campos vazios!", "warning", null, null, 3000);
        } else if(tempo.equals("") ){
          Clients.showNotification("Nao foi posivel auto avaliar-se porque o campo de data esta vazio!", "warning", null, null, 3000);  
        }
         else {
            Controlpg cpg=new Controlpg();
                modelo.Autoavaliacao au = new modelo.Autoavaliacao();
               au.setAno(new Date(ano.getValue().getDay(),ano.getValue().getMonth(),ano.getValue().getYear()));
               au.setObservacao(observacaoAuto.getText());
//        ForwardEvent evt = null;
//               
                au.setPontuacaototal(cpg.pt);
                 // Messagebox.show(cpg.pt + "queria guardar");
               try {
               new  control.AutoavaliacaoJpaController(new Jpa().getEmf()).create(au);
                    Clients.showNotification("Autoavaliado com sucesso!", "info", null, null, 3000);
                    observacaoAuto.setValue("");
               } catch (Exception e) {
                   Clients.showNotification("erro ao guardar", "warning", null, null, 3000);
               }
                   
        }
    }  

//////////////////////////////////////////////////////////////////////////////////////////////////////////    
    public void onApagaProf(final ForwardEvent evt) throws Exception {
        Messagebox.show("Apagar?", "Prompt", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
                new EventListener() {
            @Override
            public void onEvent(Event evet) throws NonexistentEntityException {
                switch (((Integer) evet.getData()).intValue()) {
                    case Messagebox.YES:
                        Button btn = (Button) evt.getOrigin().getTarget();
                        Listitem litem = (Listitem) btn.getParent().getParent();
                        Professor pf = litem.getValue();
                        pf.setIdprofessor(Short.parseShort(btn.getId()));
                        new ProfessorJpaController(new Jpa().getEmf()).destroy(Short.parseShort(btn.getId()));

                    case Messagebox.NO:
                        return;
                }
            }

        });
    }

//    @Listen("onClick = #addobser")
//    public void adicionarObservacao() {
//
//        modelo.Autoavaliacao au = new modelo.Autoavaliacao();
//        au.setAno(new Date(ano.getValue().getDay(), ano.getValue().getMonth(), ano.getValue().getYear()));
//        au.setObservacao(observacaoAuto.getText());
//        if (pt < 0) {
//            pt = (pt * (-1));
//        } else {
//            au.setPontuacaototal(pt);
//        }
//
//        try {
//            new control.AutoavaliacaoJpaController(new Jpa().getEmf()).create(au);
//            Clients.showNotification("Autoavaliado com sucesso!", "info", null, null, 3000);
//
//            observacaoAuto.setValue("");
//        } catch (Exception e) {
//            Clients.showNotification("erro ao guardar", "warning", null, null, 3000);
//        }
//
//    }
/////////////////////////////////////////       

    public void onRadio1(final ForwardEvent evt) throws Exception {

        Radio btn = (Radio) evt.getOrigin().getTarget();
        Listitem litem = (Listitem) btn.getParent().getParent();
        Parametrodocenteestudante pat = litem.getValue();
        Messagebox.show(btn.getValue() + " to a pegar");
        Parametrodocenteestudante pdest = new Parametrodocenteestudante();
        Pontuacaodocenteestudante pde = (Pontuacaodocenteestudante) new PontuacaodocenteestudanteJpaController(new Jpa().getEmf()).findPontuacaodocenteestudanteEntities();
        for (int i = 0; i < 5; i++) {
            if (pde.getIdpontuacaodocenteestudante() == 1) {
                pat.setIdpontuacaodocenteestudante(pde);
                new ParametrodocenteestudanteJpaController(new Jpa().getEmf()).create(pat);
                Messagebox.show(btn.getValue() + " foi guardado");
            }
        }
    }

    public void onRadio2(final ForwardEvent evt) throws Exception {

        Radio btn = (Radio) evt.getOrigin().getTarget();
        Listitem litem = (Listitem) btn.getParent().getParent();
        Parametrodocenteestudante pat = litem.getValue();
        Parametrodocenteestudante pdest = new Parametrodocenteestudante();
        Pontuacaodocenteestudante pde = (Pontuacaodocenteestudante) new PontuacaodocenteestudanteJpaController(new Jpa().getEmf()).findPontuacaodocenteestudanteEntities();
        for (int i = 0; i < 5; i++) {
            if (pde.getIdpontuacaodocenteestudante() == 2) {
                pat.setIdpontuacaodocenteestudante(pde);
                new ParametrodocenteestudanteJpaController(new Jpa().getEmf()).create(pat);
                Messagebox.show(btn.getValue() + " foi guardado");
            }
        }
    }

    public void onRadio3(final ForwardEvent evt) throws Exception {

        Radio btn = (Radio) evt.getOrigin().getTarget();
        Listitem litem = (Listitem) btn.getParent().getParent();
        Parametrodocenteestudante pat = litem.getValue();
        Parametrodocenteestudante pdest = new Parametrodocenteestudante();
        Pontuacaodocenteestudante pde = (Pontuacaodocenteestudante) new PontuacaodocenteestudanteJpaController(new Jpa().getEmf()).findPontuacaodocenteestudanteEntities();
        for (int i = 0; i < 5; i++) {
            if (pde.getIdpontuacaodocenteestudante() == 3) {
                pat.setIdpontuacaodocenteestudante(pde);
                new ParametrodocenteestudanteJpaController(new Jpa().getEmf()).create(pat);
                Messagebox.show(btn.getValue() + " foi guardado");
            }
        }
    }

    public void onRadio4(final ForwardEvent evt) throws Exception {

        Radio btn = (Radio) evt.getOrigin().getTarget();
        Listitem litem = (Listitem) btn.getParent().getParent();
        Parametrodocenteestudante pat = litem.getValue();
        Parametrodocenteestudante pdest = new Parametrodocenteestudante();
        Pontuacaodocenteestudante pde = (Pontuacaodocenteestudante) new PontuacaodocenteestudanteJpaController(new Jpa().getEmf()).findPontuacaodocenteestudanteEntities();
        for (int i = 0; i < 5; i++) {
            if (pde.getIdpontuacaodocenteestudante() == 4) {
                pat.setIdpontuacaodocenteestudante(pde);
                new ParametrodocenteestudanteJpaController(new Jpa().getEmf()).create(pat);
                Messagebox.show(btn.getValue() + " foi guardado");
            }
        }
    }

    public void onRadio5(final ForwardEvent evt) throws Exception {

        Radio btn = (Radio) evt.getOrigin().getTarget();
        Listitem litem = (Listitem) btn.getParent().getParent();
        Parametrodocenteestudante pat = litem.getValue();
        Parametrodocenteestudante pdest = new Parametrodocenteestudante();
        Pontuacaodocenteestudante pde = (Pontuacaodocenteestudante) new PontuacaodocenteestudanteJpaController(new Jpa().getEmf()).findPontuacaodocenteestudanteEntities();
        for (int i = 0; i < 5; i++) {
            if (pde.getIdpontuacaodocenteestudante() == 5) {
                pat.setIdpontuacaodocenteestudante(pde);
                new ParametrodocenteestudanteJpaController(new Jpa().getEmf()).create(pdest);
                Messagebox.show(btn.getValue() + " foi guardado");
            }
        }
    }
    //////////////////////////////////  

    @Command
    public void winquestao() {
        winquestao.doModal();

    }

    @Command
    public void winparametro() {
        winparametro.doModal();

    }

    @Command
    public void winparametroestudante() {
        winparametroestudante.doModal();

    }

    @Command
    public void winquestaoestu() {
        winquestaoestu.doModal();

    }

    @Command
    public void wincaracterist() {
        wincaracterist.doModal();

    }

    @Command
    public void winsuperior() {
        winsuperior.doModal();

    }

    @Command
    public void winprofe() {
        winprofe.doModal();

    }

    private Parametroautoavaliacao pau = new Parametroautoavaliacao();

    public void onChangeTextbox(ForwardEvent event) {

        Textbox tb = (Textbox) event.getOrigin().getTarget();
        System.out.println("Textbox " + tb.getId() + " has a value of: " + tb.getValue());
    }

    public List<Professor> getProf1() {
        return prof1;
    }

    public void setProf1(List<Professor> prof1) {
        this.prof1 = prof1;
    }

    public List<Professor> getListadocente() {
        return listadocente;
    }

    public void setListadocente(List<Professor> listadocente) {
        this.listadocente = listadocente;
    }

    public List<Cargochefia> getListafunc() {
        return listafunc;
    }

    public void setListafunc(List<Cargochefia> listafunc) {
        this.listafunc = listafunc;
    }

    public float getPt() {
        return pt;
    }

    public void setPt(float po) {
        pt = pt + po;
    }

    public Datebox getAno() {
        return ano;
    }

    public void setAno(Datebox ano) {
        this.ano = ano;
    }

    public List<Departamento> getListadep() {
        return listadep;
    }

    public void setListadep(List<Departamento> listadep) {
        this.listadep = listadep;
    }

    public List<Categoriaa> getListacate() {
        return listacate;
    }

    public void setListacate(List<Categoriaa> listacate) {
        this.listacate = listacate;
    }

    public Textbox getObservacaoAuto() {
        return observacaoAuto;
    }

    public void setObservacaoAuto(Textbox observacaoAuto) {
        this.observacaoAuto = observacaoAuto;
    }

    public List<Autoavaliacao> getListaAuto() {
        return listaAuto;
    }

    public void setListaAuto(List<Autoavaliacao> listaAuto) {
        this.listaAuto = listaAuto;
    }

    public List<Regime> getListaregime() {
        return listaregime;
    }

    public void setListaregime(List<Regime> listaregime) {
        this.listaregime = listaregime;
    }

    public Window getWinestuda() {
        return winestuda;
    }

    public void setWinestuda(Window winestuda) {
        this.winestuda = winestuda;
    }

    public Window getWinprofe() {
        return winprofe;
    }

    public void setWinprofe(Window winprofe) {
        this.winprofe = winprofe;
    }

    public List<Grupo> getListaGrupo() {
        return listaGrupo;
    }

    public void setListaGrupo(List<Grupo> listaGrupo) {
        this.listaGrupo = listaGrupo;
    }

    public Window getWingrupo() {
        return wingrupo;
    }

    public void setWingrupo(Window wingrupo) {
        this.wingrupo = wingrupo;
    }

    @Command
    public void winUser() {
        winUser.doModal();

    }

    @Command
    public void wingrupo() {
        wingrupo.doModal();

    }

    @Command
    public void winestuda() {
        winestuda.doModal();

    }

    public List<Estudante> getListaEstudante() {
        return listaEstudante;
    }

    public void setListaEstudante(List<Estudante> listaEstudante) {
        this.listaEstudante = listaEstudante;
    }

    public Textbox getQuantida() {
        return quantida;
    }

    public void setQuantida(Textbox quantida) {
        this.quantida = quantida;
    }

    public Window getSelf() {
        return self;
    }

    public void setSelf(Window self) {
        this.self = self;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Label getMylabel() {
        return mylabel;
    }

    public void setMylabel(Label mylabel) {
        this.mylabel = mylabel;
    }

    public Parametroautoavaliacao getPau() {
        return pau;
    }

    public void setPau(Parametroautoavaliacao pau) {
        this.pau = pau;
    }

    public List<Users> getListaUser() {
        return listaUser;
    }

    public void setListaUser(List<Users> listaUser) {
        this.listaUser = listaUser;
    }

    public Textbox getCodigo() {
        return codigo;
    }

    public void setCodigo(Textbox codigo) {
        this.codigo = codigo;
    }

    public Div getAutoavaliacao() {
        return autoavaliacao;
    }

    public void setAutoavaliacao(Div autoavaliacao) {
        this.autoavaliacao = autoavaliacao;
    }

    public Window getWinUser() {
        return winUser;
    }

    public void setWinUser(Window winUser) {
        this.winUser = winUser;
    }

    public List<Caracteristiquestaosuperior> getListacaracteristic() {
        return listacaracteristic;
    }

    public void setListacaracteristic(List<Caracteristiquestaosuperior> listacaracteristic) {
        this.listacaracteristic = listacaracteristic;
    }

    public Window getWincaracterist() {
        return wincaracterist;
    }

    public List<Questaodocentesuperior> getListaquestsuper() {
        return listaquestsuper;
    }

    public void setListaquestsuper(List<Questaodocentesuperior> listaquestsuper) {
        this.listaquestsuper = listaquestsuper;
    }

    public Window getWinsuperior() {
        return winsuperior;
    }

    // Geters e seters
    public void setWinsuperior(Window winsuperior) {
        this.winsuperior = winsuperior;
    }

    public void setWincaracterist(Window wincaracterist) {
        this.wincaracterist = wincaracterist;
    }

    public List<Questaodocenteestudante> getListaQuestEstuda() {
        return listaQuestEstuda;
    }

    public void setListaQuestEstuda(List<Questaodocenteestudante> listaQuestEstuda) {
        this.listaQuestEstuda = listaQuestEstuda;
    }

    public Window getWinquestaoestu() {
        return winquestaoestu;
    }

    public void setWinquestaoestu(Window winquestaoestu) {
        this.winquestaoestu = winquestaoestu;
    }

    public Window getWinparametroestudante() {
        return winparametroestudante;
    }

    public void setWinparametroestudante(Window winparametroestudante) {
        this.winparametroestudante = winparametroestudante;
    }

    public List<Parametrodocenteestudante> getListaParametroestuda() {
        return listaParametroestuda;
    }

    public void setListaParametroestuda(List<Parametrodocenteestudante> listaParametroestuda) {
        this.listaParametroestuda = listaParametroestuda;
    }

    public Window getWinquestao() {
        return winquestao;
    }

    public void setWinquestao(Window winquestao) {
        this.winquestao = winquestao;
    }

    public List<Questaoautoavaliacao> getListaQuestAutoAval() {
        return listaQuestAutoAval;
    }

    public void setListaQuestAutoAval(List<Questaoautoavaliacao> listaQuestAutoAval) {
        this.listaQuestAutoAval = listaQuestAutoAval;
    }

    public Window getWinparametro() {
        return winparametro;
    }

    public void setWinparametro(Window winparametro) {
        this.winparametro = winparametro;
    }

    public List<Parametroautoavaliacao> getListaParamAutoAval() {
        return listaParamAutoAval;
    }

    public void setListaParamAutoAval(List<Parametroautoavaliacao> listaParamAutoAval) {
        this.listaParamAutoAval = listaParamAutoAval;
    }

    public List<Parametrodocenteestudante> getListaparametro() {
        return listaparametro;
    }

    public void setListaparametro(List<Parametrodocenteestudante> listaparametro) {
        this.listaparametro = listaparametro;
    }

    public List<Questaodocentesuperior> getListaparametrosuper() {
        return listaparametrosuper;
    }

    public void setListaparametrosuper(List<Questaodocentesuperior> listaparametrosuper) {
        this.listaparametrosuper = listaparametrosuper;
    }

    public String getPesqNome() {
        return pesqNome;
    }

    public void setPesqNome(String pesqNome) {
        this.pesqNome = pesqNome;
    }

    public Controlpg() {
        this.pt = pt;
    }

}
