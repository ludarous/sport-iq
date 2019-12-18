export const backendUrl = 'http://localhost:9090';

export const environment = {
    production: true,
    apiUrl: backendUrl + '/api',
    backendUrl,
    logoutUrl: 'http://ludarous.com:2080/auth/realms/dev/protocol/openid-connect/logout',
    allowedLoginReferrers: ['*'],
};
