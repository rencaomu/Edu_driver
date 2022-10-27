module.exports = {

    // some nuxt config...
    plugins: [
      { src: '~/plugins/nuxt-swiper-plugin.js', ssr: false }
    ],
    css: [
      'swiper/dist/css/swiper.css'
    ],
  /*
  ** Headers of the page
  */
  head: {
    title: '种草课堂 - 你学习上的助力者',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'keywords', name: 'keywords', content: '种草课堂,IT在线视频教程,Java视频,HTML5视频,前端视频,Python视频,大数据视频' },
      { hid: 'description', name: 'description', content: '种草课堂是国内领先的IT在线视频学习平台、职业教育平台。！' }
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/icon.png'}
    ]
  },
  /*
  ** Customize the progress bar color
  */
  loading: { color: '#3B8070' },
  /*
  ** Build configuration
  */
  build: {
    /*
    ** Run ESLint on save
    */
    extend (config, { isDev, isClient }) {
      if (isDev && isClient) {
        config.module.rules.push({
          enforce: 'pre',
          test: /\.(js|vue)$/,
          loader: 'eslint-loader',
          exclude: /(node_modules)/
        })
      }
    }
  }
}

