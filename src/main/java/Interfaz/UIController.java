package Interfaz;

import Excepciones.ArchiveIOException;
import Negocio.EGPaso;
import Persistencia.ArchiveWriter;
import Persistencia.SistemaArchivos;
import Soporte.Acumulador;
import Soporte.JavaArray;
import Soporte.JavaOAHashTable;
import Soporte.VotoLista;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Clase de Interfaz, la cual, tiene como objetivo controlar la actividad de la Interfaz Gráfica de Usuario y la
 * llamada a los métodos de recuperación de datos de los archivos, para poder ser procesados por esta clase.
 *
 * La clase contiene varios atributos, de los cuales uno de ellos "paso2019" es el que se utiliza para almacenar los
 * votos contabilizados y las ubicaciones existentes en el pais. El resto de los atributos, son utilizados para el
 * control de la Interfaz Gráfica de Usuario.
 *
 * @author Facundo L. Acosta - Lucas Paillet - Camila Cocuzza
 */
public class UIController implements Initializable {

    private EGPaso paso2019;
    private JavaOAHashTable<Integer, String> listasCandidatos = new JavaOAHashTable<>();
    private TableColumn<VotoLista, Integer> idNumeroLista = new TableColumn<>("Número de Lista");
    private TableColumn<VotoLista, String> idNombreLista = new TableColumn<>("Nombre de Lista");
    private TableColumn<VotoLista, Integer> idVotosLista = new TableColumn<>("Cantidad de Votos");
    public ComboBox<String> idBoxDistrito;
    public ComboBox<String> idBoxSeccion;
    public ComboBox<String> idBoxCircuito;
    public ComboBox<String> idBoxMesa;
    public TableView idTablaResultados;

    /**
     * Método de inicialización para el Framework JavaFX, la misma inicializa y procesa la carga de datos de los
     * archivos y configura la tabla donde se visualizaran los datos necesarios en la interfaz gráfica.
     *
     * @param fxmlFileLocation
     * @param resources
     */
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        try {
            System.out.println("\nAguarde un instante, se estan procesando los Archivos Necesarios.");

            SistemaArchivos manejadorDeArchivos = new SistemaArchivos();
            manejadorDeArchivos.loadPostulaciones(this.listasCandidatos);
            this.paso2019 = new EGPaso(this.listasCandidatos);
            manejadorDeArchivos.loadRegiones(this.paso2019);
            manejadorDeArchivos.loadTotales(this.paso2019);

            // Por cuestiones lógicas de la des-serialización en cuanto a tiempo de demora de mas de un minuto, solo
            // se agregó, la serialización de los datos procesados.
            try {
                ArchiveWriter writer = new ArchiveWriter("dataArchivePASO2019.tsb");

                System.out.println("\tAguarde un instante, se estan grabando los datos procesados en Disco Local.");
                writer.writer(this.paso2019);
                System.out.println("\tGrabación finalizada con éxito.");
            } catch (ArchiveIOException exW) {
                exW.printStackTrace();
            }

            System.out.println("Procesamiento Finalizado.");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        System.out.println("\nEjecutando Interfaz Gráfica.");

        JavaArray<String> listaDistritos = paso2019.listaNombresUbicaciones(-1, "");
        this.idBoxDistrito.setItems(FXCollections.observableArrayList(listaDistritos));

        idNumeroLista.setCellValueFactory(new PropertyValueFactory<>("numeroLista"));
        idNumeroLista.setMaxWidth(125);
        idNumeroLista.setStyle("-fx-alignment: CENTER;");
        idNombreLista.setCellValueFactory(new PropertyValueFactory<>("nombreLista"));
        idNombreLista.setMaxWidth(371);
        idNombreLista.setStyle("-fx-alignment: CENTER;");
        idVotosLista.setCellValueFactory(new PropertyValueFactory<>("cantidadVotos"));
        idVotosLista.setMaxWidth(125);
        idVotosLista.setStyle("-fx-alignment: CENTER;");
        idVotosLista.setSortType(TableColumn.SortType.DESCENDING);

        this.idTablaResultados.getColumns().setAll(idNumeroLista, idNombreLista, idVotosLista);
        this.idTablaResultados.getColumns().addListener(new ListChangeListener() {
            private boolean suspended;

            /*
             * Método para que no se le permita al usuario la movilización de las columnas de la tabla.
             *
             * @param change
             */
            @Override
            public void onChanged(Change change) {
                change.next();
                if (change.wasReplaced() && !suspended) {
                    this.suspended = true;
                    idTablaResultados.getColumns().setAll(idNumeroLista, idNombreLista, idVotosLista);
                    this.suspended = false;
                }
            }
        });
    }

    /**
     * Método asignado como controlador para el box de distritos. Este método, inserta al box de distritos, el listado
     * de todos los distritos existentes.
     *
     * @param actionEvent
     */
    public void actionDistrito(ActionEvent actionEvent) {
        if (!this.idBoxDistrito.getValue().equals("Todo")) {
            String distritoSeleccionado = this.idBoxDistrito.getValue().substring(0, 2);
            this.idBoxSeccion.getItems().clear();
            JavaArray<String> listaSecciones = paso2019.listaNombresUbicaciones(1, distritoSeleccionado);
            this.idBoxSeccion.setItems(FXCollections.observableArrayList(listaSecciones));
            this.idBoxCircuito.getItems().clear();
            this.idBoxMesa.getItems().clear();
        } else {
            this.idBoxSeccion.getItems().clear();
            this.idBoxCircuito.getItems().clear();
            this.idBoxMesa.getItems().clear();
        }

    }

    /**
     * Método asignado como controlador para el box de secciones. Este método, inserta al box de secciones, el listado
     * de todos las secciones existentes de acuerdo al distrito seleccionado.
     *
     * @param actionEvent
     */
    public void actionSeccion(ActionEvent actionEvent) {
        if (this.idBoxSeccion.getValue() != null) {
            if (!this.idBoxSeccion.getValue().equals("Todo")) {
                String distritoSeleccionado = this.idBoxDistrito.getValue().substring(0, 2);
                String seccionSeleccionada = distritoSeleccionado + this.idBoxSeccion.getValue().substring(0, 3);
                JavaArray<String> listaCircuitos = paso2019.listaNombresUbicaciones(2, seccionSeleccionada);
                this.idBoxCircuito.getItems().clear();
                this.idBoxCircuito.setItems(FXCollections.observableArrayList(listaCircuitos));
                this.idBoxMesa.getItems().clear();
            } else {
                this.idBoxCircuito.getItems().clear();
                this.idBoxMesa.getItems().clear();
            }
        }
    }

    /**
     * Método asignado como controlador para el box de circuitos. Este método, inserta al box de circuitos, el listado
     * de todos los distritos existentes de acuerdo a la seccion seleccionada.
     *
     * @param actionEvent
     */
    public void actionCircuito(ActionEvent actionEvent) {
        if (this.idBoxCircuito.getValue() != null) {
            if (!this.idBoxCircuito.getValue().equals("Todo")) {
                String distritoSeleccionado = this.idBoxDistrito.getValue().substring(0, 2);
                String seccionSeleccionada = this.idBoxSeccion.getValue().substring(0, 3);
                String circuitoSeleccionado = distritoSeleccionado + seccionSeleccionada + this.idBoxCircuito.getValue().substring(0, 6);
                JavaArray<String> listaMesas = paso2019.listaNombresUbicaciones(3, circuitoSeleccionado);
                this.idBoxMesa.setItems(FXCollections.observableArrayList(listaMesas));
            } else {
                this.idBoxMesa.getItems().clear();
            }
        }
    }

    /**
     * Método asignado como controlador para el boton que realiza el filtrado. Este método, controla que el box de
     * distritos tenga algún distrito seleccionado y en caso de que no, ejecuta una alerta; a su vez, inserta a la
     * tabla de visualización, las listas con el número de lista, el nombre de lista y la cantidad de votos de la lista
     * de acuerdo a los elementos seleccionados en cada box.
     *
     * @param actionEvent
     */
    public void actionFiltrar(ActionEvent actionEvent) {
        if (this.idBoxDistrito.getValue() == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Error de Filtrado");
            alerta.setHeaderText("Debe seleccionar un Distrito para realizar el filtrado.");
            alerta.showAndWait();
        } else {
            JavaOAHashTable<Integer, Acumulador> votos = null;
            this.idTablaResultados.getItems().clear();

            String codigoUbicacion;
            if (!this.idBoxDistrito.getValue().equals("Todo")) {
                codigoUbicacion = this.idBoxDistrito.getValue().substring(0, 2);
            } else { codigoUbicacion = ""; }

            if (this.idBoxSeccion.getValue() != null && !this.idBoxSeccion.getValue().equals("Todo")) {
                codigoUbicacion = codigoUbicacion.substring(0, 2) + this.idBoxSeccion.getValue().substring(0, 3);
            }

            if (this.idBoxCircuito.getValue() != null && !this.idBoxCircuito.getValue().equals("Todo")) {
                codigoUbicacion = codigoUbicacion.substring(0, 5) + this.idBoxCircuito.getValue().substring(0, 6);
            }

            if (this.idBoxMesa.getValue() != null && !this.idBoxMesa.getValue().equals("Todo")) {
                codigoUbicacion = codigoUbicacion.substring(0, 11) + this.idBoxMesa.getValue();
            }

            switch (codigoUbicacion.length()) {
                default: votos = this.paso2019.votacionesFiltradas(-1, codigoUbicacion); break;
                case 2: votos = this.paso2019.votacionesFiltradas(1, codigoUbicacion); break;
                case 5: votos = this.paso2019.votacionesFiltradas(2, codigoUbicacion); break;
                case 11: votos = this.paso2019.votacionesFiltradas(3, codigoUbicacion); break;
                case 17: votos = this.paso2019.votacionesFiltradas(4, codigoUbicacion); break;
            }

            for (Map.Entry<Integer, String> nextElement : this.listasCandidatos.entrySet()) {
                if (nextElement.getKey() != null) {
                    int cantidadVotos = votos.get(nextElement.getKey()).getAcumulador();
                    VotoLista votoLista = new VotoLista(nextElement.getKey(), nextElement.getValue(), cantidadVotos);
                    this.idTablaResultados.getItems().add(votoLista);
                }
            }

            this.idTablaResultados.getSortOrder().add(this.idVotosLista);
        }
    }
}
