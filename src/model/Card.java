package model;


/** 
 * For Card, you have to define attributes that may help you determine the card's identity.
 * 
 * Inheritance approach:
 * The Card class extends Label
 * Label is to hold the image of the card.
 * You may extend to ImageView instead of Label.
 * Useful functions for Label
 * getGraphic(), setGraphic(), setPreferedSize(), getText(), setText(), setTextAlignment().
 * Useful functions for ImageView
 * setImage(), setPreferedSize().
 * Warning:
 * One ImageView for one Label. You can't have more than one Label setting the same image as the graphic.
 * Use width 100px and height 200px.
 * 
 * Composition approach:
 * You may, not use the inheritance that is given. You may have the card return the image to View.
 * But you are responsible to change the View accordingly.
 * 
 * Create image according to name approach:
 * The card may have nothing to do with the image, you have to change the View to create image from the Control's input.
 * I THINK THIS IS THE BEST FOR YOU.
 * 
 */
public class Card{
    
    public Card(){
        
    }
    
}
