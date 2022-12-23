package otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import otus.model.Book;


@Service
@RequiredArgsConstructor
public class PermissionService {

    private final MutableAclService aclService;

    @Transactional
    public void addPermissionForUser(Book targetObj, Permission permission, String username) {
        final Sid sid = new PrincipalSid(username);
        addPermissionForSid(targetObj, permission, sid);
    }

    private void addPermissionForSid(Book targetObj, Permission permission, Sid sid) {
        ObjectIdentity oi = new ObjectIdentityImpl(targetObj.getClass(), targetObj.getId());
        MutableAcl acl = null;
        try {
            acl = (MutableAcl) aclService.readAclById(oi);
        } catch (NotFoundException nfe) {
            acl = aclService.createAcl(oi);
        }
        acl.insertAce(acl.getEntries().size(), permission, sid, true);
        aclService.updateAcl(acl);

    }
}