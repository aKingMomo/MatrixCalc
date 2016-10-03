/**
 * Created by alexi on 5/8/2016.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;

public class CalculatorMain {
    private static JTextField[][] Aspaces;
    private static JTextField[][] Bspaces;
    private static JPanel results;
    private static JFrame frame;

    public static void main(String[] args) {
        initialize();
    }

    private static void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 800, 300);
        frame.setLayout(new BorderLayout());

        frame.getContentPane().setBackground(Color.PINK);

        setUIFont(new javax.swing.plaf.FontUIResource("Comic Sans MS", Font.PLAIN, 12));

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.PINK);
        frame.add(topPanel, BorderLayout.NORTH);

        JLabel matrixAlabel = new JLabel("Matrix A");
        topPanel.add(matrixAlabel);

        JTextField Arows = new JTextField(7);
        Arows.setText("rows");
        topPanel.add(Arows);

        JTextField Acolumns = new JTextField(7);
        Acolumns.setText("columns");
        topPanel.add(Acolumns);

        JLabel matrixBlabel = new JLabel("Matrix B");
        topPanel.add(matrixBlabel);

        JTextField Brows = new JTextField(7);
        Brows.setText("rows");
        topPanel.add(Brows);

        JTextField Bcolumns = new JTextField(7);
        Bcolumns.setText("columns");
        topPanel.add(Bcolumns);

        final JPanel[] centerPanel = {new JPanel()};

        JButton setDimensionsButton = new JButton("Set");
        topPanel.add(setDimensionsButton);
        setDimensionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    frame.remove(centerPanel[0]);
                    int aR = Integer.parseInt(Arows.getText());
                    int aC = Integer.parseInt(Acolumns.getText());
                    int bR = Integer.parseInt(Brows.getText());
                    int bC = Integer.parseInt(Bcolumns.getText());
                    centerPanel[0] = createCenterPanel(aR, aC, bR, bC);
                    centerPanel[0].setBackground(Color.PINK);
                    frame.add(centerPanel[0], BorderLayout.CENTER);
                    frame.setVisible(true);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Make sure all inputs are integers!");
                }
            }
        });

        frame.add(createBottomPanel(), BorderLayout.SOUTH);

        frame.setVisible(true);

    }

    private static JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.PINK);
        bottomPanel.setLayout(new BorderLayout());

        results = new JPanel();
        results.setBackground(Color.PINK);
        bottomPanel.add(results, BorderLayout.NORTH);

        JButton[] aButtons = new JButton[5];
        JPanel[] aPanels = new JPanel[2];

        JButton[] bButtons = new JButton[5];
        JPanel[] bPanels = new JPanel[2];

        JButton[] abButtons = new JButton[3];
        JPanel[] Panels = new JPanel[3];

        Panels[0] = createMButtonPanel(aButtons, aPanels, true);
        Panels[1] = createABButtonPanel(abButtons);
        Panels[2] = createMButtonPanel(bButtons, bPanels, false);

        JPanel buttons = new JPanel();
        for (int i = 0; i < 3; i++) {
            buttons.add(Panels[i]);
            Panels[i].setBackground(Color.PINK);
        }
        bottomPanel.add(buttons, BorderLayout.SOUTH);

        return bottomPanel;
    }

    private static JPanel createABButtonPanel(JButton[] abButtons) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.PINK);
        panel.setLayout(new GridLayout(3, 1));

        abButtons[0] = new JButton("A*B");
        abButtons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Aspaces[0].length != Bspaces.length) {
                    JOptionPane.showMessageDialog(null, "The number columns of the first matrix must equal the amount of rows in the second");
                } else {
                    results.removeAll();
                    int[][] matrixA = getMatrix(Aspaces);
                    int[][] matrixB = getMatrix(Bspaces);
                    Solution sol = new Solution();
                    sol.setMatrix(matrixB);
                    sol.setEdited(false);
                    MatrixCalculations calc = new MatrixCalculations(matrixA, sol, "MULTIPLYAB");
                    Thread a = new Thread(calc);
                    a.start();
                    while (!sol.isDone()) {
                        System.out.println("waiting");
                    }
                    int[][] ans = sol.getMatrix();
                    setResultMatrix(ans);
                }
            }
        });

        abButtons[1] = new JButton("A+B");
        abButtons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Aspaces.length != Bspaces.length || Aspaces[0].length != Bspaces[0].length) {
                    JOptionPane.showMessageDialog(null, "Matrices must be the same size!");
                } else {
                    results.removeAll();
                    int[][] matrixA = getMatrix(Aspaces);
                    int[][] matrixB = getMatrix(Bspaces);
                    Solution sol = new Solution();
                    sol.setMatrix(matrixB);
                    sol.setEdited(false);
                    MatrixCalculations calc = new MatrixCalculations(matrixA, sol, "ADD");
                    Thread a = new Thread(calc);
                    a.start();
                    while (!sol.isDone()) {
                        System.out.println("waiting");
                    }
                    int[][] ans = sol.getMatrix();
                    setResultMatrix(ans);
                }
            }
        });

        abButtons[2] = new JButton("A-B");
        abButtons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Aspaces.length != Bspaces.length || Aspaces[0].length != Bspaces[0].length) {
                    JOptionPane.showMessageDialog(null, "Matrices must be the same size!");
                } else {
                    results.removeAll();
                    int[][] matrixA = getMatrix(Aspaces);
                    int[][] matrixB = getMatrix(Bspaces);
                    Solution sol = new Solution();
                    sol.setMatrix(matrixB);
                    sol.setEdited(false);
                    MatrixCalculations calc = new MatrixCalculations(matrixA, sol, "SUB");
                    Thread a = new Thread(calc);
                    a.start();
                    while (!sol.isDone()) {
                        System.out.println("waiting");
                    }
                    int[][] ans = sol.getMatrix();
                    setResultMatrix(ans);
                }
            }
        });

        for (int i = 0; i < 3; i++) {
            panel.add(abButtons[i]);
        }
        return panel;
    }

    private static int[][] getMatrix(JTextField[][] spaces) {
        int[][] matrix = new int[spaces.length][spaces.length];
        for (int i = 0; i < spaces.length; i++) {
            for (int j = 0; j < spaces.length; j++) {
                matrix[i][j] = Integer.parseInt(spaces[i][j].getText());
            }
        }
        return matrix;
    }

    private static JPanel createMButtonPanel(JButton[] buttons, JPanel[] panels, boolean isA) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.PINK);
        panel.setLayout(new BorderLayout());

        panels[0] = new JPanel();
        panel.add(panels[0]);

        buttons[2] = new JButton("Find Determinant");
        buttons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    results.removeAll();
                    if (isA) {
                        if (Aspaces.length != Aspaces[0].length) {
                            JOptionPane.showMessageDialog(null, "Matrix must be a square!");
                        } else {
                            getDeterminant(Aspaces);
                        }
                    } else {
                        if (Bspaces.length != Bspaces[0].length) {
                            JOptionPane.showMessageDialog(null, "Matrix must be a square!");
                        } else {
                            getDeterminant(Bspaces);
                        }
                    }
                } catch (NumberFormatException ex) {

                }
            }

            private void getDeterminant(JTextField[][] spaces) {
                int[][] matrix = getMatrix(spaces);
                Solution solution = new Solution();
                MatrixCalculations calc = new MatrixCalculations(matrix, solution, "DETERMINANT");
                Thread a = new Thread(calc);
                a.start();
                while (!solution.isDone()) {
                }
                JLabel result = new JLabel("The determinant is equal to " + String.valueOf(solution.getDeterminant()));
                results.add(result);
                frame.setVisible(true);
            }
        });

        buttons[1] = new JButton("Clear");
        buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                results.removeAll();
                if (isA) {
                    for (int i = 0; i < Aspaces.length; i++) {
                        for (int j = 0; j < Aspaces[i].length; j++) {
                            Aspaces[i][j].setText("");
                        }
                    }
                } else {
                    for (int i = 0; i < Bspaces.length; i++) {
                        for (int j = 0; j < Bspaces[i].length; j++) {
                            Bspaces[i][j].setText("");
                        }
                    }
                }
                frame.setVisible(true);
            }
        });
        panels[0].add(buttons[1]);
        panels[0].add(buttons[2]);

        panel.add(panels[0], BorderLayout.CENTER);

        panels[1] = new JPanel();
        JTextField multiplyBy = new JTextField(1);

        buttons[3] = new JButton("Multiply by ");
        buttons[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    results.removeAll();
                    Solution solution = new Solution();
                    solution.setDeterminant(Integer.parseInt(multiplyBy.getText()));
                    solution.setEdited(false);
                    int[][] matrix;
                    if (isA) {
                        matrix = getMatrix(Aspaces);
                    } else {
                        matrix = getMatrix(Bspaces);
                    }
                    MatrixCalculations calc = new MatrixCalculations(matrix, solution, "MULTIPLYBYC");
                    Thread a = new Thread(calc);
                    a.start();
                    while (!solution.isDone()) {
                        System.out.println("waiting");
                    }
                    int[][] sol = solution.getMatrix();
                    setResultMatrix(sol);
                } catch (NumberFormatException ex) {

                }
            }
        });
        panels[1].add(buttons[3]);

        panels[1].add(multiplyBy);

        JTextField power = new JTextField(1);

        buttons[4] = new JButton("Raise to power ");
        buttons[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                results.removeAll();
                Solution solution = new Solution();
                solution.setDeterminant(Integer.parseInt(power.getText()));
                solution.setEdited(false);
                int[][] matrix;
                if (isA) {
                    matrix = getMatrix(Aspaces);
                } else {
                    matrix = getMatrix(Bspaces);
                }
                MatrixCalculations calc = new MatrixCalculations(matrix, solution, "RAISEPOWER");
                Thread a = new Thread(calc);
                a.start();
                while (!solution.isDone()) {
                    System.out.println("waiting");
                }
                int[][] sol = solution.getMatrix();
                setResultMatrix(sol);
            }
        });
        panels[1].add(buttons[4]);


        panels[1].add(power);

        panel.add(panels[1], BorderLayout.SOUTH);

        for (int i = 0; i < panels.length; i++) {
            panels[i].setBackground(Color.PINK);
        }

        return panel;
    }

    private static void setResultMatrix(int[][] matrix) {
        results.setLayout(new GridLayout(matrix.length, matrix[0].length));
        JTextField[][] labels = new JTextField[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                labels[i][j] = new JTextField(2);
                labels[i][j].setText(String.valueOf(matrix[i][j]));
                results.add(labels[i][j]);
            }
        }
        frame.setVisible(true);
    }

    private static JPanel createCenterPanel(int aR, int aC, int bR, int bC) {
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.PINK);

        JPanel[] mPanels = new JPanel[2];

        Aspaces = new JTextField[aR][aC];
        Bspaces = new JTextField[bR][bC];

        JLabel aLabel = new JLabel("Matrix A");
        centerPanel.add(aLabel);

        mPanels[0] = createMatrixInput(aR, aC, Aspaces);
        centerPanel.add(mPanels[0]);

        JLabel bLabel = new JLabel("Matrix B");
        centerPanel.add(bLabel);

        mPanels[1] = createMatrixInput(bR, bC, Bspaces);
        centerPanel.add(mPanels[1]);

        return centerPanel;
    }

    private static JPanel createMatrixInput(int r, int c, JTextField[][] spaces) {
        JPanel matrixInputPanel = new JPanel();
        matrixInputPanel.setBackground(Color.PINK);
        matrixInputPanel.setLayout(new GridLayout(r, c));
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                spaces[i][j] = new JTextField(2);
                matrixInputPanel.add(spaces[i][j]);
            }
        }
        return matrixInputPanel;
    }

    public static void setUIFont(javax.swing.plaf.FontUIResource f) {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) UIManager.put(key, f);
        }
    }
}
