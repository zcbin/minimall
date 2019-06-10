var app = getApp();
var util = require('../../utils/util.js');
var api = require('../../config/api.js');

Page({
  data: {
    comments: [], //评论内容
    type: 0,
    gid: 0,
    
    allCount: 0,
    page: 1, //当前页
    limit: 10
  },
  // getCommentCount: function() {
  //   let that = this;
  //   util.request(api.CommentCount, {
  //     gid: that.data.gid,
  //     type: that.data.type
  //   }).then(function(res) {
  //     if (res.errno === 0) {
  //       that.setData({
  //         allCount: res.data.allCount,
  //         hasPicCount: res.data.hasPicCount
  //       });
  //     }
  //   });
  // },
  getCommentList: function() {
    
    let that = this;
    // console.log(that.data.page);
    util.request(api.CommentList, {
      gid: that.data.gid,
      type: that.data.type,
     
      page: that.data.page,
      limit: that.data.limit,
     
    }).then(function(res) {
      if (res.errno === 0) {
        that.setData({
          allCount: res.data.count,
          page: res.data.currentPage,
          comments: that.data.comments.concat(res.data.data)
        });
        
      }
    });
  },
  onLoad: function(options) {
    // 页面初始化 options为页面跳转所带来的参数
    this.setData({
      type: options.type,
      gid: options.gid
    });
    // this.getCommentCount();
    this.getCommentList();
  },
  onReady: function() {
    // 页面渲染完成

  },
  onShow: function() {
    // 页面显示

  },
  onHide: function() {
    // 页面隐藏

  },
  onUnload: function() {
    // 页面关闭

  },
  switchTab: function() {
    let that = this;
    that.setData({
  
      page: 1,
      comments: []
   
    });
    
    this.getCommentList();
  },
  onReachBottom: function() {
    // console.log('onPullDownRefresh'+this.data.allCount);
    if (this.data.page * this.data.limit >= this.data.allCount) {
      return false;
    }


    this.setData({
      'page': this.data.page + 1
    });
    

    this.getCommentList();
  }
})