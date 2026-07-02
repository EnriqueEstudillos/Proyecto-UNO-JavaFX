package Teoria;

/**
 *
 * @author estui
 */
public class Teoria {
    public static void main(String[] args) {
        String cadena = "500";
        int numero;

        if (isNumeric(cadena) == true) {
            numero = Integer.parseInt(cadena);
            System.out.println("Numero: " + numero);
        } else {
            System.out.println("No es un numero");
        }
    }
    
    // Comprobar si string es numerico:
    public static boolean isNumeric(String cadena) {

        boolean resultado;

        try {
            Integer.valueOf(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }
    
    // Combo box:
    /* 
    Declaración elemento en Controller.java
    @FXML
    private ComboBox<String> tfgenero;

    Evento btnagregar selecciona elemento del combo box
    @FXML
    void btnagregar(ActionEvent event) {
        String genero = tfgenero.getSelectionModel().getSelectedItem().toString();
    }

    Iniciaizamos valores del combo box
    @FXML
    void initialize() {
        // Agregamos las opciones al ComboBox de género
        tfgenero.getItems().addAll("Genero", "Acción", "Comedia", "Drama", "Ciencia Ficcion", "Thriler");
        // Establecemos la opción predeterminada
        tfgenero.setValue("Genero");
    }
    */
    
    /* Labels:
    
    @FXML
    Label myLabel;
    
    String valuePropery = "Hello World!"
    myLabel = new Label("Start");
    myLabel.textProperty().bind(valueProperty);
    
    myLabel.setText("Hello World!");
    */
    
    /* ListView
    
    @FXML
    ListView<Carta> ListCartas;
    
    ListCartas.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    ListCartas.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    
    button.setOnAction(event -> {
        ObservableList selectedIndices = listView.getSelectionModel().getSelectedIndices();

        for(Object o : selectedIndices){
            System.out.println("o = " + o + " (" + o.getClass() + ")");
        }
    });

    ListView<String> listView = new ListView<String>();

    ObservableList<String> list = FXCollections.observableArrayList();
    listView.setItems(list);
    list.add("item1");
    list.add("item2");
    list.add("item3");

    ListCartas.getSelectionModel().selectedItemProperty().addListener(this::selectionChanged);
    
    public void UpdateListCartas(){
        ListCartas.getItems().clear();
        for (int i = 0;i < jugador.getMano().size();i++){
            ListCartas.getItems().add(jugador.getMano().get(i));
        }
    }
    
    private void selectionChanged(ObservableValue<? extends Carta> Observable, Carta oldVal, Carta newVal){
        if (ListCartas.getSelectionModel().getSelectedItem() == null){
            try{
                //System.out.println("/imagenes/Cartas/"+newVal.getNombre()+newVal.getColor()+".png");
                Image imagen = new Image("/imagenes/Cartas/"+oldVal.getNombre()+oldVal.getColor()+".png");
                CartaSeleccionada.setImage(imagen);
                Selecion = oldVal;
            }catch (Exception e){
                System.out.println("Error ImagenCarta: "+e);
            }
        }
        else{
            try{
                //System.out.println("/imagenes/Cartas/"+newVal.getNombre()+newVal.getColor()+".png");
                Image imagen = new Image("/imagenes/Cartas/"+newVal.getNombre()+newVal.getColor()+".png");
                CartaSeleccionada.setImage(imagen);
                Selecion = newVal;
            }catch (Exception e){
                System.out.println("Error ImagenCarta: "+e);
            }
        }
    }
    */
}
