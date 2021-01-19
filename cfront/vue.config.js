module.exports = {
    publicPath: './',
    assetsDir: 'static',
    productionSourceMap: false,
    devServer: {
        proxy: {
            '/api':{
                target:'http://139.159.183.141:8090',
                // target:'http://localhost:8090',
                changeOrigin:true,
                pathRewrite:{
                    '/api':''
                }
            }
        }
    }
}
