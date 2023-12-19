package OrderFacade;

import Factory.Item.A_Item;

import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList < A_Item > selectedItems = new ArrayList < > ();

    public void addItem(A_Item item) {
        if(item.getAmount() > 0){
        selectedItems.add(item);
        item.setAmount(item.getAmount()-1);
        }
        else{
            System.out.println("Not enough quantity");
        }
    }

    public void removeItem(A_Item item) {
        selectedItems.remove(item);
    }

    public void clearCart(){
        selectedItems.clear();
    }

    public ArrayList < A_Item > getItems() {
        return selectedItems;
    }
}

//Delete Items -- admin
//    public static void removeItem(A_Item item) {
//        if (connection != null) {
//            String deleteStatement = "DELETE FROM Items WHERE ItemID = ?";
//
//            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteStatement)) {
//                preparedStatement.setInt(1, item.getItemID());
//                preparedStatement.executeUpdate();
//            } catch (SQLException e) {
//                e.printStackTrace();
//                // Handle the exception appropriately, log or throw as needed
//            }
//        }