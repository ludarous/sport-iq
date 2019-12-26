export const backendUrl = 'http://backend.sportiq.cz';

export const environment = {
    production: true,
    apiUrl: backendUrl + '/api',
    backendUrl,
    logoutUrl: 'http://ludarous.com:2080/auth/realms/dev/protocol/openid-connect/logout',
    allowedLoginReferrers: ['*'],
};
