// index.js
// 获取应用实例
const app = getApp()

// import rsa from '../../libs/jsencrypt-3.0.0-rc.1.js'
import rsa from '../../libs/jsencrypt-3.2.1.min.js'

Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    canIUseGetUserProfile: false,
    canIUseOpenData: wx.canIUse('open-data.type.userAvatarUrl') && wx.canIUse('open-data.type.userNickName') // 如需尝试获取用户信息可改为false
  },
  // 事件处理函数
  bindViewTap() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad() {
    if (wx.getUserProfile) {
      this.setData({
        canIUseGetUserProfile: true
      })
    }
  },

 /**
  * 生命周期函数--监听页面显示
  */
 onShow: function () {
  // https://developers.weixin.qq.com/miniprogram/dev/api/network/request/wx.request.html
  wx.request({
    url: 'http://127.0.0.1:8080/session', //仅为示例，并非真实的接口地址
    data: {
      x: '',
      y: ''
    },
    header: {
      'content-type': 'application/json' // 默认值
    },
    success(res) {
      console.log(res.data)
    }
  })
 
  wx.request({
     url: 'http://127.0.0.1:8080/rsa/get-public-key',
     success(res) {
       var publicKey = res.data.publicKey
       console.log('公钥：', publicKey)
       var encrypt = rsa.JSEncrypt
       console.log(rsa)
       encrypt.prototype.setPublicKey(publicKey);
       var data = '你好，世界。'
       console.log('加密前：', data)
       var encrypt = encrypt.prototype.encrypt(data);
       console.log('加密后：', encrypt)
 
       wx.request({
         url: 'http://127.0.0.1:8080/rsa/decrypt',
         data: {
           encrypt: encrypt
         },
         success(resp) {
           console.log('后端解密：', resp.data)
         }
       })
     }
   })

},

  getUserProfile(e) {
    // 推荐使用wx.getUserProfile获取用户信息，开发者每次通过该接口获取用户个人信息均需用户确认，开发者妥善保管用户快速填写的头像昵称，避免重复弹窗
    wx.getUserProfile({
      desc: '展示用户信息', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
      success: (res) => {
        console.log(res)
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    })
  },
  getUserInfo(e) {
    // 不推荐使用getUserInfo获取用户信息，预计自2021年4月13日起，getUserInfo将不再弹出弹窗，并直接返回匿名的用户个人信息
    console.log(e)
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  }
})