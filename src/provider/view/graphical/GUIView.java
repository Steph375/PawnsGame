package provider.view.graphical;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import provider.controller.ViewActions;
import provider.model.Card;
import provider.model.PlayerTeam;
import provider.model.PlayingCard;
import provider.model.ReadonlyPawnsBoard;
import provider.view.graphical.actions.GraphicalKeyListener;

/**
 * A GUI view for the PawnsBoard game.
 */
public class GUIView extends JFrame implements PawnsBoardView {

  private final JPanel handPanel;
  private final PawnsBoardPanel boardPanel;
  private final JLabel turnLabel;
  private final JPanel leftScorePanel;
  private final JPanel rightScorePanel;
  private final ReadonlyPawnsBoard model;
  private final List<CardPanel> cardPanels = new ArrayList<>();
  private final PlayerTeam team;
  private ViewActions viewActions;

  /**
   * Constructor for the GUIView.
   *
   * @param model model that is visualized in a GUI.
   * @param team  the player this view is for.
   */
  public GUIView(ReadonlyPawnsBoard model, PlayerTeam team) {
    super("Pawns Board Game");
    this.model = model;
    this.team = team;
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    //player turn at the top
    turnLabel = new JLabel(model.getNextTurn().toString(),
        SwingConstants.CENTER);
    turnLabel.setFont(new Font("Arial", Font.BOLD, 16));
    add(turnLabel, BorderLayout.NORTH);
    //left and right score panels.
    leftScorePanel = new JPanel();
    leftScorePanel.setPreferredSize(new Dimension(50, 400));
    leftScorePanel.setBackground(Color.WHITE);
    add(leftScorePanel, BorderLayout.WEST);

    rightScorePanel = new JPanel();
    rightScorePanel.setPreferredSize(new Dimension(50, 400));
    rightScorePanel.setBackground(Color.WHITE);
    add(rightScorePanel, BorderLayout.EAST);
    //board in the center
    boardPanel = new PawnsBoardPanel(model);
    add(boardPanel, BorderLayout.CENTER);
    //hand in the bottom
    handPanel = new JPanel();
    handPanel.setLayout(new FlowLayout());
    JScrollPane handScroll = new JScrollPane(handPanel);
    handScroll.setPreferredSize(new Dimension(800, 200));
    add(handScroll, BorderLayout.SOUTH);

    pack();
    setLocationRelativeTo(null);
  }

  @Override
  public void setActionsListener(ViewActions actions) {
    this.viewActions = actions;
    boardPanel.setMouseListener(actions);
    this.addKeyListener(new GraphicalKeyListener(actions));
  }

  @Override
  public void updateHand(ViewActions viewActions) {
    handPanel.removeAll();
    cardPanels.clear();
    for (int i = 0; i < model.getPlayerHand(team).size(); i++) {
      PlayingCard c = model.getPlayerHand(team).get(i);
      CardPanel panel = getCardPanel(viewActions, c, i);
      cardPanels.add(panel);
      handPanel.add(panel);
    }
    handPanel.revalidate();
    handPanel.repaint();
    this.repaint();
  }

  private CardPanel getCardPanel(ViewActions controller, PlayingCard c, int i) {
    CardPanel panel = new CardPanel((Card) c, i, team);
    panel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        for (CardPanel cp : cardPanels) {
          cp.setSelected(false);
        }
        panel.setSelected(true);

        controller.setSelectedCard(i);
      }
    });
    return panel;
  }

  @Override
  public void clearSelection() {
    boardPanel.clearSelection();
  }


  @Override
  public void updateTurnLabel() {
    String s = "current turn: " + model.getNextTurn();
    if (model.getNextTurn() == team) {
      s = s.concat(". Press space to pass");
    }
    turnLabel.setText(s);
  }

  /**
   * Refreshes the view.
   */
  @Override
  public void updateView() {
    boardPanel.repaint();
  }

  /**
   * Creates the view window.
   */
  @Override
  public void makeVisible() {
    setVisible(true);
  }

  @Override
  public void updateScores() {
    leftScorePanel.removeAll();
    rightScorePanel.removeAll();

    int rows = model.getRowSize();
    leftScorePanel.setLayout(new GridLayout(rows, 1));
    rightScorePanel.setLayout(new GridLayout(rows, 1));

    for (int i = 0; i < rows; i++) {
      int redScore = model.scoreRows(PlayerTeam.RED).get(i);
      int blueScore = model.scoreRows(PlayerTeam.BLUE).get(i);

      JLabel redLabel = new JLabel(String.valueOf(redScore), SwingConstants.CENTER);
      JLabel blueLabel = new JLabel(String.valueOf(blueScore), SwingConstants.CENTER);
      redLabel.setFont(new Font("Arial", Font.PLAIN, 16));
      blueLabel.setFont(new Font("Arial", Font.PLAIN, 16));

      leftScorePanel.add(redLabel);
      rightScorePanel.add(blueLabel);
    }
    leftScorePanel.revalidate();
    leftScorePanel.repaint();
    rightScorePanel.revalidate();
    rightScorePanel.repaint();
  }

  @Override
  public void update() {
    this.updateTurnLabel();
    this.updateScores();
    this.updateView();
    this.updateHand(viewActions);
  }

  @Override
  public void warnPlayer(String s) {
    JOptionPane.showMessageDialog(null, s, "alert", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void gameOver(PlayerTeam winner) {
    viewActions.gameOver();
    if (winner != null) {
      warnPlayer("game over. " + winner + " wins! gg");
    } else {
      warnPlayer("game over. it was a Tie!");
    }
  }

}