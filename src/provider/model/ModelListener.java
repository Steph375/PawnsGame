package provider.model;

/**
 * A class can implement this and then register to the model to be notified.
 */
public interface ModelListener {

  /**
   * The model will call this whenever the state of the ame changes.
   */
  void onModelChanged();
}
