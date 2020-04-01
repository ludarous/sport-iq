import { AuthoritiesEnum } from '../app/dashboard/entities/authorities';

export const backendUrl = 'https://api.sportiq.cz';
export const authoritiesEnum = AuthoritiesEnum;

export const environment = {
    production: true,
    apiUrl: backendUrl + '/api',
    backendUrl,
    logoutUrl: 'https://auth.dev.sportiq.cz/auth/realms/dev/protocol/openid-connect/logout',
    allowedLoginReferrers: ['*'],
    authoritiesEnum,
};
