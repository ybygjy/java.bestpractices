package org.ybygjy.basic.annotation.brfw.entity;

import java.util.List;

import org.ybygjy.basic.annotation.brfw.anno.Exportable;
import org.ybygjy.basic.annotation.brfw.anno.Persistent;

/**
 * Address list
 * @author WangYanCheng
 * @version 2009-12-16
 */
@Exportable(
        name = "AddressList",
        description = "address List"
)
public class FriendList {
    /**friend name*/
    @Persistent
    private String friendName;
    /**friend age*/
    @Persistent
    private String friendAge;
    /**friend telephone*/
    private String friendTel;
    /**friend Address*/
    @Persistent
    private List<Address> friendAddress;
    /**note*/
    private String note;
    /**
     * Address List contructor
     * @param friendName friendName to set
     * @param friendAge friendAge to set
     * @param friendTel friendTel to set
     * @param friendAddress friendAddress to set
     * @param note note to set
     */
    public FriendList(String friendName, String friendAge, String friendTel,
            List<Address> friendAddress, String note) {
        this.friendName = friendName;
        this.friendAge = friendAge;
        this.friendTel = friendTel;
        this.friendAddress = friendAddress;
        this.note = note;
    }
    /**
     * getFriendName
     * @return friendName
     */
    @Persistent
    public String getFriendName() {
        return friendName;
    }
    /**
     * set friendName
     * @param friendName friendName
     */
    @Persistent
    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }
    /**
     * getFriendAge
     * @return friendAge
     */
    public String getFriendAge() {
        return friendAge;
    }
    /**
     * friendAge
     * @param friendAge friendAge
     */
    public void setFriendAge(String friendAge) {
        this.friendAge = friendAge;
    }
    /**
     * getFriendTel
     * @return friendTel
     */
    public String getFriendTel() {
        return friendTel;
    }
    /**
     * setFriendTel
     * @param friendTel friendTel
     */
    public void setFriendTel(String friendTel) {
        this.friendTel = friendTel;
    }
    /**
     * getFriendAddress
     * @return friendAddress
     */
    public List<Address> getFriendAddress() {
        return friendAddress;
    }
    /**
     * setFriendAddress
     * @param friendAddress friendAddress
     */
    public void setFriendAddress(List<Address> friendAddress) {
        this.friendAddress = friendAddress;
    }
    /**
     * getNote
     * @return note
     */
    public String getNote() {
        return note;
    }
    /**
     * setNote
     * @param note note
     */
    public void setNote(String note) {
        this.note = note;
    }
}
