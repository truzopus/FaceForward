import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Profile {

    private String firstName;
    private String lastName;
    private int age;
    private String emailAddress;
    private String passWord;

    private List<Items> recentItems;
    private List<Items> wishlist;
    private List<Items> purchases;
    private List<Items> recommendations;

    private Items item1 = new Items("macbook pro", "laptops");
    private Items item2 = new Items("controller", "xbox1");
    private Items item3 = new Items("go pro", "cameras");
    private Items item4 = new Items("samsung", "phones");

    public Profile(String firstName, String lastName, String emailAddress, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.passWord = password;

        recentItems = new ArrayList<Items>();
        wishlist = new ArrayList<Items>();
    }

    public Profile() {
        this.firstName = null;
        this.lastName = null;
        this.emailAddress = null;
        this.passWord = null;

        recentItems = new ArrayList<Items>();
        wishlist = new ArrayList<Items>();
    }

    public void setFirstName(String name) {
        this.firstName = firstName;
    }

    public void setLastName(String name) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.emailAddress = email;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void addrecentItem(Items item) {
        recentItems.add(item);
    }

    public void removerecentItem(Items item) {
        for (int i = 0; i < recentItems.size(); i++) {
            if (Objects.equals(recentItems.get(i), item)) {
                recentItems.remove(i);

            }
        }
    }

    public List<Items> getRecommendations() {
        return recommendations;
    }

    public void addWishListItem(Items item) {
        wishlist.add(item);
    }

    public void removeWishlistItem(Items item) {
        for (int i = 0; i < wishlist.size(); i++) {
            if (Objects.equals(wishlist.get(i), item)) {
                wishlist.remove(i);
            }
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return emailAddress;
    }

    public List<Items> getRecentItems() {
        return recentItems;
    }

    public void setJill() {
        this.firstName = "Jill";
        this.lastName = "Bao";
        this.emailAddress = "jill.ba00@gmail.com";
        this.passWord = "123456789";
        this.age = 19;

        recommendations.add(item1);
        recommendations.add(item2);

        recommendations.add(item3);
        recommendations.add(item4);
    }

    public void setAlex() {
        this.firstName = "Alex";
        this.lastName = "Lin";
        this.emailAddress = "alxdanderlin@gmail.com";
        this.passWord = "987654321";
        this.age = 19;
        recommendations.add(item1);
        recommendations.add(item2);
        recommendations.add(item3);
    }

    public void setAlan() {
        this.firstName = "Alan";
        this.lastName = "Milligan";
        this.emailAddress = "alanjmilligan@gmail.com";
        this.passWord = "12123434";
        this.age = 19;
        recommendations.add(item1);
        recommendations.add(item2);
    }


}
