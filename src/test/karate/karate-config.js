function fn() {
    var serverPort = karate.properties['server.port'] || '8093'

    var config = {
        baseUrl: 'http://localhost:' + serverPort + '/api',
        debugExchange: true,
    }

    karate.configure('readTimeout', 300000)
    karate.configure('logPrettyRequest', true)
    karate.configure('logPrettyResponse', true)

    return config
}
