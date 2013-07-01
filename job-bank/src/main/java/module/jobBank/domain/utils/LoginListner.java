package module.jobBank.domain.utils;

import module.organization.domain.PartyType;
import module.organization.domain.PartyType.PartyTypeBean;
import module.organization.domain.Person;
import module.webserviceutils.client.JerseyRemoteUser;
import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.bennu.core.applicationTier.AuthenticationListner;
import pt.ist.bennu.core.domain.User;
import pt.utl.ist.fenix.tools.util.i18n.Language;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class LoginListner implements AuthenticationListner {

    @Override
    public void afterLogin(final UserView userView) {
        final User user = userView.getUser();
        importUserInformation(user);
    }

    public static void importUserInformation(final String username) {
        final User user = User.findByUsername(username);
        if (user != null) {
            importUserInformation(user);
        }
    }

    private static void importUserInformation(final User user) {
        if (user.getPerson() == null) {
            updateUserInformation(user);
        }
    }

    private static void updateUserInformation(final User user) {
        JerseyRemoteUser remotePerson = new JerseyRemoteUser(user);
        if (remotePerson != null) {
            final MultiLanguageString partyName = new MultiLanguageString(Language.pt, remotePerson.getName());
            final PartyType partyType = getPersonPartyType();

            final Person person = Person.create(partyName, partyType);
            user.setPerson(person);
        }
    }

    public static PartyType getPersonPartyType() {
        PartyType partyType = PartyType.readBy(Person.class.getName());
        if (partyType == null) {
            final PartyTypeBean partyTypeBean = new PartyTypeBean();
            partyTypeBean.setType(Person.class.getName());
            partyTypeBean.setName(new MultiLanguageString(Language.pt, "Pessoa"));
            partyType = PartyType.create(partyTypeBean);
        }
        return partyType;
    }

}
