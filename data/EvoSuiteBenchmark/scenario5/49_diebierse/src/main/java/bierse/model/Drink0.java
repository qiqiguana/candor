package bierse.model;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import bierse.view.MyKeyMap;

/**
 * @author Rainer Friesen
 */
public class Drink {

    /**
     * Recalculate the current price for this drink
     */
    public int recalculate() {
        //Calculate new average values
        totalSold += lastSold;
        averageAmount = (float) totalSold / model.getIteration();
        averagePrice = ((averagePrice * (model.getIteration() - 1)) + currentPrice) / model.getIteration();
        if (totalSold > 0) {
            averageSoldPrice = ((averageSoldPrice * (totalSold - lastSold)) + (currentPrice * lastSold)) / totalSold;
        }
        int newPrice;
        if (directPrice != 0) {
            // If a direct price was set, use it
            newPrice = directPrice;
            directPrice = 0;
        } else if (keepPrice) {
            // Keep the price
            newPrice = currentPrice;
        } else {
            // Calculate the new price
            double diffSold = lastSold - targetAmount;
            double factor = diffSold / deltaAmount;
            double change = factor * maxStep;
            int priceSteps = model.getSettings().getPriceSteps();
            double rest = Math.floor(Math.abs(change % priceSteps));
            double full = Math.floor(Math.abs(change / priceSteps));
            int priceChange = 0;
            if (rest >= priceSteps / 2) {
                priceChange = priceSteps;
            }
            priceChange += full * priceSteps;
            // Check that the change of the price is not more than the max step
            if (priceChange > maxStep) {
                priceChange = maxStep;
            }
            if (model.getSettings().isStandardLogic()) {
                if (factor >= 0) {
                    newPrice = currentPrice + priceChange;
                } else {
                    newPrice = currentPrice - priceChange;
                }
            } else {
                // Inverse logic
                if (factor >= 0) {
                    newPrice = currentPrice - priceChange;
                } else {
                    newPrice = currentPrice + priceChange;
                }
            }
        }
        // Check that the new price is not above the max price or below the min price
        if (newPrice > maxPrice) {
            newPrice = maxPrice;
        } else if (newPrice < minPrice) {
            newPrice = minPrice;
        }
        // Calculate the trend
        if (newPrice > currentPrice) {
            trend = 1;
        } else if (newPrice < currentPrice) {
            trend = -1;
        } else {
            trend = 0;
        }
        currentPrice = newPrice;
        //Debug output
        model.getLog().debug("Recalculating drink: " + name + " for iteration: " + model.getIteration());
        model.getLog().debug("Last sold: " + lastSold);
        model.getLog().debug("Total sold:" + totalSold);
        model.getLog().debug("Average sold: " + averageAmount);
        model.getLog().debug("Average price: " + averagePrice);
        model.getLog().debug("Average sold price: " + averageSoldPrice);
        model.getLog().debug("New price: " + currentPrice);
        // Reset values for the next iteration
        lastSold = 0;
        return currentPrice;
    }
}

public class Settings {

    public int getPriceSteps();

    public boolean isStandardLogic();
}

/**
 * @author Rainer Friesen
 */
public class Model {

    public int getIteration();

    public Settings getSettings();

    public Logger getLog();
}
