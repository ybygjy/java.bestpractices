/**
 * @class Nstc.webgate.Test
 * @depJs Nstc.other.CommonTreeWindow
 * @depJs Nstc.form.MoneyField
 * @depJs Nstc.other.PrintSelectorWindow
 * @description
 */
Nstc.webgate.Test = Ext.extend(Ext.Panel, {
	signUrl : Nstc.Main.getContextPath() + '/signTest.do',
	initComponent : function() {
		this.buttons = [{
			text : '验证',
			scope : this,
			handler : function() {
				// Nstc.util.Sign.createSign();
				// window.setTimeout(function(){
				// Nstc.util.Sign.detachedSign('abc','2');
				// },4000);
				// signUrl
				var content = "abc";
				signObj.defaultDN = 'CN=041@398492364297364@sxcw@00000008,OU=Enterprises,OU=TEST,O=CFCA TEST CA,C=CN';
				signObj.plainTextForDetachedSign = content;
				signObj.makeDetachedSign();
				var signature = signObj.detachedSign;
				alert(signature);
				this.ddd.setValue(signature);
				if (Ext.isEmpty(signature)) {
					throw new Error("x must not be negative");
				}
				Ext.Ajax.request({
							url : this.signUrl,
							method : 'POST',
							params : {
								content : content,
								signData : signature
							},
							scope : this,
							callback : function(op, success, response) {

							}
						})

			}
		}];
		this.tbar = this.createTopBar();
		this.ddd = new Ext.form.TextArea({
					width : 500,
					height : 500
				});
		this.items = this.ddd;
		Nstc.webgate.Test.superclass.initComponent.call(this);
	},

	createTopBar : function() {
		this.urltext = new Ext.form.TextField();
		return [new Nstc.form.MoneyField({
							allowNegative : true
						}), '-', {
					text : 'treewindow测试',
					scope : this,
					handler : function() {
						this.w = new Nstc.other.CommonTreeWindow({
									width : 400,
									height : 400,
									url : Nstc.Main.getContextPath()
											+ '/column-data.json',
									cm : [{
												header : '名称',
												width : 360,
												dataIndex : 'task'
											}]
								});
						this.w.show();
					}
				}, '-', {
					text : '打印测试按钮',
					scope : this,
					handler : function() {
						new Nstc.other.PrintSelectorWindow({
									initData : [{
												field1 : 'aaaa'
											}, {
												field1 : 'bbbb'
											}],
									isReloadData : false,
									printFuncCode : 'ofiVoucher'
								});
					}
				}, '-', this.urltext, {
					text : 'url编码测试',
					scope : this,
					handler : function() {
						this.ddd.setValue(encodeURIComponent(this.urltext
								.getValue()));
					}
				}];
	}
});