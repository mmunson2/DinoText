package Camden;

public class CamdenTest
{
    private static CamdenModel model;
    private static CamdenView view;
    private static CamdenController controller;

    public static void main(String[] args)
    {
        model = new CamdenModel();
        view = new CamdenView();
        controller = new CamdenController(model, view);

        view.setVisible(true);

        String str = "The quick brown fox ran extremely quickly and jumped over the lazy dog in order to escape from the crazed beast that was chasing it.";

        controller.setDialogue(str);
        controller.update();
    }
}