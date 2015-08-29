/*
 *
 *  Copyright (C) 2015 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.lyeung.elwood.vcs.command;

/**
 * Created by lyeung on 17/07/2015.
 */
public class CloneCommandParam {

    public enum AuthenticationType {
        NONE, USERNAME_PASSWORD, PUBLIC_KEY_PASSPHRASE
    }

    private String remoteUri;

    private String localDirectory;

    private final Authentication authentication = new Authentication();

    CloneCommandParam() {
        // do-nothing
    }

    public String getRemoteUri() {
        return remoteUri;
    }

    public void setRemoteUri(String remoteUri) {
        this.remoteUri = remoteUri;
    }

    public String getLocalDirectory() {
        return localDirectory;
    }

    public void setLocalDirectory(String localDirectory) {
        this.localDirectory = localDirectory;
    }

    public boolean isUsernamePasswordAuthentication() {
        return getAuthenticationType() == AuthenticationType.USERNAME_PASSWORD;
    }

    public boolean isUsePublicKeyAuthentication() {
        return getAuthenticationType() == AuthenticationType.PUBLIC_KEY_PASSPHRASE;
    }

    public AuthenticationType getAuthenticationType() {
        return authentication.getAuthenticationType();
    }

    public void setAuthenticationType(AuthenticationType authenticationType) {
        authentication.setAuthenticationType(authenticationType);
    }

    public String getUsername() {
        return authentication.getUsername();
    }

    public void setUsername(String username) {
        authentication.setUsername(username);
    }

    public String getPassword() {
        return authentication.getPassword();
    }

    public void setPassword(String password) {
        authentication.setPassword(password);
    }

    public String getIdentityKey() {
        return authentication.getIdentityKey();
    }

    public void setIdentityKey(String identityKey) {
        authentication.setIdentityKey(identityKey);
    }

    public String getPassphrase() {
        return authentication.getPassphrase();
    }

    public void setPassphrase(String passphrase) {
        authentication.setPassphrase(passphrase);
    }

    /**
     * Authentication info.
     */
    private static class Authentication {

        private AuthenticationType authenticationType = AuthenticationType.NONE;

        private final UsernamePassword usernamePassword = new UsernamePassword();

        private final IdentityKeyPassphrase identityKeyPassphrase = new IdentityKeyPassphrase();

        public AuthenticationType getAuthenticationType() {
            return authenticationType;
        }

        public void setAuthenticationType(AuthenticationType authenticationType) {
            if (authenticationType == null) {
                throw new IllegalArgumentException("authentication type cannot be null");
            }

            this.authenticationType = authenticationType;
        }

        public String getUsername() {
            return usernamePassword.getUsername();
        }

        public void setUsername(String username) {
            usernamePassword.setUsername(username);
        }

        public String getPassword() {
            return usernamePassword.getPassword();
        }

        public void setPassword(String password) {
            usernamePassword.setPassword(password);
        }

        public String getIdentityKey() {
            return identityKeyPassphrase.getIdentityKey();
        }

        public void setIdentityKey(String identityKey) {
            identityKeyPassphrase.setIdentityKey(identityKey);
        }

        public String getPassphrase() {
            return identityKeyPassphrase.getPassphrase();
        }

        public void setPassphrase(String passphrase) {
            identityKeyPassphrase.setPassphrase(passphrase);
        }

        private static class UsernamePassword {

            private String username;

            private String password;

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }
        }

        /**
         * Use identity key and passphrase.
         */
        private static class IdentityKeyPassphrase {

            private String identityKey;

            private String passphrase;

            public String getIdentityKey() {
                return identityKey;
            }

            public void setIdentityKey(String identityKey) {
                this.identityKey = identityKey;
            }

            public String getPassphrase() {
                return passphrase;
            }

            public void setPassphrase(String passphrase) {
                this.passphrase = passphrase;
            }
        }
    }
}
