public class Main {
    public static void main(String[] args) {

        Interface im = new Interface();

        im.initComponents();
        im.createPanels();
        im.renderTable();
        im.renderSidePanel();
        im.refresh();
    }
}