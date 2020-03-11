// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

import { Authorities, AuthoritiesEnum } from '../app/admin/entities/authorities';

export const backendUrl = 'http://localhost:9090';
export const authoritiesEnum = AuthoritiesEnum;
export const environment = {
    production: false,
    apiUrl: backendUrl + '/api',
    backendUrl,
    logoutUrl: 'http://auth.dev.sportiq.cz/auth/realms/dev/protocol/openid-connect/logout',
    allowedLoginReferrers: ['*'],
    authoritiesEnum,
};
