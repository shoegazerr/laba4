import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.event.MouseAdapter;

public class FractalExplorer {
    private int winSize;
    private JImageDisplay display;
    private FractalGenerator generator;
    private Rectangle2D.Double rectangle;

    public FractalExplorer(int winSize){
        this.winSize = winSize;
        this.rectangle = new Rectangle2D.Double();
        this.generator = new Mandelbrot();
        this.generator.getInitialRange(this.rectangle);
        this.display = new JImageDisplay(winSize, winSize);
    }

    public void createAndShowGUI (){
        MouseAdapter mListener = new MListener();
        ActionListener actionListener = new AListener(); // объект для отслеживания всех нажатий

        JFrame frame = new JFrame("Фрактал");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display.setLayout(new BorderLayout()); //точка вывода
        display.addMouseListener(mListener); // добавление отслеживания нажатий клавиш мыши
        frame.add(display, BorderLayout.CENTER); // добавили изображение
        // фрактала в окно и установили место вывода изображения фрактала
        JButton button = new JButton("Сброс");
        button.addActionListener(actionListener);
        frame.add(button, BorderLayout.SOUTH); // добавили в окно

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
    private void drawFractal(){
        int rgbColor;
        for (int x=0; x<winSize; x++){
            for (int y = 0; y<winSize; y++){
                double xCoord = generator.getCoord(rectangle.x, rectangle.x + rectangle.width,
                        winSize, x);
                double yCoord = generator.getCoord(rectangle.y, rectangle.y + rectangle.width,
                        winSize, y);
                int iters = generator.numIterations(xCoord, yCoord);
                if (iters == -1) // задаем черный цвет, если вышли за границы
                    rgbColor = 0;
                else { // иначе задем цвет на основании количества интераций
                    float hue = 0.7f + (float) iters / 200f;
                    rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                }

                display.drawPixel(x, y, rgbColor);
            }
        }
        display.repaint();
    }

    public class AListener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            display.clearImage();
            generator.getInitialRange(rectangle); // запускаем генератор, который задает диапозон построения фрактала
            drawFractal(); // рисуем фрактал
        }
    }

    public class MListener extends MouseAdapter{
        public void mouseClicked(MouseEvent event) {
            double x = event.getPoint().getX(); // получаю координаты нажатия
            double y = event.getPoint().getY();

            x = generator.getCoord(rectangle.x, rectangle.x + rectangle.width, winSize, (int) x);
            y = generator.getCoord(rectangle.y, rectangle.y + rectangle.width, winSize, (int) y);

            generator.recenterAndZoomRange(rectangle, x, y, 0.5);
            drawFractal();
            display.repaint();
        }
    }
    public static void main (String agrs []){
        FractalExplorer fractalExplorer = new FractalExplorer(700);
        fractalExplorer.createAndShowGUI();
        fractalExplorer.drawFractal();
    }
}
