/**
 * tree组件
 * Copyright (c) 2009-2016 www.szkingdom.com. All rights reserved.
 */
define("lib/kui/tree", [], function(require, exports, module) {
    $.fn.tree = function(options, param) {
        if (typeof options == "string") {
            return $.fn.tree.methods[options](this, param);
        }
        options = options || {};
        return this.each(function() {
            var state = $.data(this, "tree"), //获取tree的属性，用来判断是否已经初始化
            opts;
            //存放tree的属性
            if (state) {
                opts = $.extend(state.options, options);
            } else {
                opts = $.extend({}, $.fn.tree.defaults, $.fn.tree.parseOptions(this), options);
                $.data(this, "tree", {
                    options: opts,
                    tree: wrapTree(this)
                });
                var contents = getContents(this);
                if (contents.length && !opts.data) {
                    opts.data = contents;
                }
            }
            if (opts.data) {
                opts.base = opts.data;
                loadData(this, this, opts.data);
            }
            if (opts.lines) {
                $(this).addClass("tree-lines");
            }
            request(this, this);
        });
    };
    $.fn.tree.methods = {
        options: function(jq) {
            return $.data(jq[0], "tree").options;
        },
        loadData: function(jq, data) {
            return jq.each(function() {
                loadData(this, this, data);
            });
        },
        getNode: function(jq, _e2) {
            return getNode(jq[0], _e2);
        },
        getData: function(jq, _e3) {
            return getData(jq[0], _e3);
        },
        reload: function(jq, _e4) {
            return jq.each(function() {
                if (_e4) {
                    var _e5 = $(_e4);
                    var hit = _e5.children("span.tree-hit");
                    hit.removeClass("tree-expanded tree-expanded-hover").addClass("tree-collapsed");
                    _e5.next().remove();
                    expand(this, _e4);
                } else {
                    $(this).empty();
                    request(this, this);
                }
            });
        },
        getRoot: function(jq) {
            return getRoot(jq[0]);
        },
        getRoots: function(jq) {
            return getRoots(jq[0]);
        },
        getParent: function(jq, _e6) {
            return getParent(jq[0], _e6);
        },
        getChildren: function(jq, _e7) {
            return getChildren(jq[0], _e7);
        },
        getChecked: function(jq) {
            return getChecked(jq[0]);
        },
        getCascadeCheckNode: function(jq) {
            return getCascadeCheckNode(jq[0]);
        },
        getSelectedBase: function(jq) {
            return getSelectedBase(jq[0]);
        },
        getSelected: function(jq) {
            return getSelected(jq[0]);
        },
        isLeaf: function(jq, _e8) {
            return getHitNode(jq[0], _e8);
        },
        find: function(jq, id) {
            return find(jq[0], id);
        },
        select: function(jq, _e9) {
            return jq.each(function() {
                onSelect(this, _e9);
            });
        },
        check: function(jq, _ea) {
            return jq.each(function() {
                setCheckbox1(this, _ea, true);
            });
        },
        checkNoCascade: function(jq, _ea) {
            return jq.each(function() {
                setCheckboxNoCascadeCheck(this, _ea, true);
            });
        },
        uncheck: function(jq, _eb) {
            return jq.each(function() {
                setCheckbox1(this, _eb, false);
            });
        },
        collapse: function(jq, _ec) {
            return jq.each(function() {
                collapse(this, _ec);
            });
        },
        expand: function(jq, _ed) {
            return jq.each(function() {
                expand(this, _ed);
            });
        },
        collapseAll: function(jq, _ee) {
            return jq.each(function() {
                collapseAll(this, _ee);
            });
        },
        expandAll: function(jq, _ef) {
            return jq.each(function() {
                expandAll(this, _ef);
            });
        },
        expandTo: function(jq, _f0) {
            return jq.each(function() {
                expandTo(this, _f0);
            });
        },
        checkedAll: function(jq) {
            return jq.each(function() {
                checkedAll(this);
            });
        },
        unChecked: function(jq) {
            return jq.each(function() {
                unChecked(this);
            });
        },
        unCheckedAll: function(jq) {
            return jq.each(function() {
                unCheckedAll(this);
            });
        },
        toggle: function(jq, _f1) {
            return jq.each(function() {
                toggle(this, _f1);
            });
        },
        append: function(jq, _f2) {
            return jq.each(function() {
                append(this, _f2);
            });
        },
        insert: function(jq, _f3) {
            return jq.each(function() {
                insert(this, _f3);
            });
        },
        remove: function(jq, _f4) {
            return jq.each(function() {
                remove(this, _f4);
            });
        },
        pop: function(jq, _f5) {
            var param = jq.tree("getData", _f5);
            jq.tree("remove", _f5);
            return param;
        },
        update: function(jq, _f7) {
            return jq.each(function() {
                update(this, _f7);
            });
        },
        enableDnd: function(jq) {
            return jq.each(function() {
                dragFile(this);
            });
        },
        disableDnd: function(jq) {
            return jq.each(function() {
                draDisable(this);
            });
        },
        beginEdit: function(jq, _f8) {
            return jq.each(function() {
                beginEdit(this, _f8);
            });
        },
        endEdit: function(jq, _f9) {
            return jq.each(function() {
                endEdit(this, _f9);
            });
        },
        cancelEdit: function(jq, _fa) {
            return jq.each(function() {
                cancelEdit(this, _fa);
            });
        },
        setCascade: function(jq, flag) {
            return jq.each(function() {
                var opts = $.data(this, "tree").options;
                opts.cascadeCheck = flag;
            });
        }
    };
    $.fn.tree.parseOptions = function(_fb) {
        var t = $(_fb);
        return $.extend({}, $.parser.parseOptions(_fb, [ "url", "method", {
            checkbox: "boolean",
            cascadeCheck: "boolean",
            onlyLeafCheck: "boolean"
        }, {
            animate: "boolean",
            lines: "boolean",
            dnd: "boolean"
        } ]));
    };
    $.fn.tree.defaults = {
        url: null,
        method: "post",
        animate: false,
        animateSpeed: 100,
        checkbox: false,
        cascadeCheck: true,
        onlyLeafCheck: false,
        lines: false,
        //节点形状控制，false为三角形，true为加减号
        dnd: false,
        //是否允许节点拖拽,拖拽事件的添加，必须放在节点数据load完成之后
        dropMethod: null,
        data: null,
        required: false,
        base: null,
        req: null,
        conf: {},
        //特殊数据结构树的改造，改造成易识别的data结构，配合makeTreeData函数使用
        /**
		特殊数据构造树,形如：
		conf:{
			nodeId:"menu_id",//树节点id字段名
			nodeName:"menu_name",//树节点显示字段名
			treeType:"1",//树类型，1：父级 2：层级
			parNode:"par_menu", //树父节点字段名：树类型为1
			lvlNode:"menu_lvl",//树层级字段：树类型为2时用此字段可以分析出其父节点
			lvlLength:"2",//树层级的长度，例如menu_lvl为0102，lvlLength为2 那么其父节点的menu_lvl为截取0102的前两个字段为menu_lvl为01对应的nodeId
			nodeDataType:"number" //配置项 如果为number ，则会将node里的id和pid先强制转换为number型，再转为string型
		},
		*/
        treeExpand: 0,
        //tree展开标识，0-不展开，1-展开第一层 2-展开第二层 …… all-展开所有
        /**
         * 默认tree的加载方法，可覆盖。request时调用。
         * @param param
         * @param success ajax访问成功调用
         * @param error ajax访问失败调用
         * @returns {boolean}
         */
        loader: function(param, success, error) {
            var that = this, opts = $(that).tree("options");
            if ((!opts.req || opts.req.length == 0) && !opts.url) {
                return false;
            }
            if (opts.url) {
                ajax.ajax({
                    url: opts.url,
                    type: opts.method
                }).done(function(res) {
                    success(opts.loaderSuccessFilter.call(this, res));
                }).fail(function() {
                    error.apply(that, arguments);
                });
            } else if (opts.req && opts.req.length > 0) {
                ajax.post({
                    req: opts.req
                }).done(function(res) {
                    success(opts.loaderSuccessFilter.call(this, res));
                }).fail(function() {
                    error.apply(that, arguments);
                });
            }
        },
        /**
         * 对于ajax请求过来的数据集，进行一次过滤，转化为后续处理易识别的结果集。只在loader中调用
         * @param data 后台直接返回的结果集，包含了请求的头文件信息，需要进行过滤。
         * @returns {*}
         */
        loaderSuccessFilter: function(data) {
            return data && data[0] || data.ANSWERS[0].ANS_COMM_DATA[0];
        },
        /**
         * 在结果集加载前过滤结果集，在loadDate中调用。
         * @param data 已经是Array类型的结果集。
         * @returns {*}
         */
        loadFilter: function(data) {
            return data;
        },
        onBeforeLoad: function(node, _101) {},
        onLoadSuccess: function(node, data) {},
        onLoadError: function() {},
        onClick: function(node) {},
        onDblClick: function(node) {},
        onBeforeExpand: function(node) {},
        onExpand: function(node) {},
        onBeforeCollapse: function(node) {},
        onCollapse: function(node) {},
        onCheck: function(node, _102) {},
        onBeforeSelect: function(node) {},
        onSelect: function(node) {},
        onContextMenu: function(e, node) {},
        onDrop: function(_103, _104, _105) {},
        onBeforeEdit: function(node) {},
        onAfterEdit: function(node) {},
        onCancelEdit: function(node) {},
        onBeforeDrag: function(e) {},
        onStartDrag: function(e) {},
        onDrag: function(e) {},
        onStopDrag: function(e) {},
        onRevert: function(e) {},
        /**
         * 将具有树层次结构的数据转换成树结构数据
         * conf 里面增加了 nodeDataType 配置项 如果为number ，则会将node里的id和pid先强制转换为number型，再转为string型
         * @param conf
         * @param data
         * @returns {Array}
         */
        makeTreeData: function(conf, data) {
            var treeType = conf.treeType;
            var lvlLength = conf.lvlLength;
            var id, pid, text, iconCls;
            if (treeType == "1") {
                id = conf.nodeId;
                pid = conf.parNode;
                text = conf.nodeName;
            } else if (treeType == "2" || treeType == "3") {
                id = conf.nodeId;
                pid = conf.lvlNode;
                text = conf.nodeName;
            }
            iconCls = conf.iconCls || "";
            var nodeData = [];
            if (treeType == "1") {
                for (var i = 0; i < data.length; i++) {
                    var node = {}, clsArray = [];
                    //根据配置对id,pid做数据类型转换 @zhaozz 2015-11-17
                    data[i][id] = "number" === conf.nodeDataType ? Number(data[i][id]) + "" : data[i][id];
                    data[i][pid] = "number" === conf.nodeDataType ? Number(data[i][pid]) + "" : data[i][pid];
                    node.id = data[i][id] || "";
                    $.each(iconCls.split(","), function() {
                        clsArray.push(data[i][this] || this);
                    });
                    node.iconCls = clsArray.join(" ");
                    var texts = text.split(",");
                    var dispField = "";
                    for (var j = 0; j < texts.length; j++) {
                        dispField += (typeof data[i][texts[j]] == "undefined" ? "" : data[i][texts[j]]) + " ";
                    }
                    node.text = dispField || "";
                    //node.pid = data[i][pid] == "-1" ? "0" : (data[i][pid] || "");
                    node.pid = data[i][pid] || "";
                    //不需要把父节点为-1的改为0.
                    nodeData.push(node);
                }
            } else if (treeType == "2") {
                data = kui.quickSort(data, pid);
                for (var i = 0; i < data.length; i++) {
                    var node = {}, clsArray = [];
                    //根据配置对id,pid做数据类型转换 @zhaozz 2015-11-17
                    // data[i][id] = ("number" === conf.nodeDataType)? Number(data[i][id])+"" : data[i][id];
                    //data[i][pid] = ("number" === conf.nodeDataType)? Number(data[i][pid])+"" : data[i][pid];
                    node.id = data[i][id] || "";
                    $.each(iconCls.split(","), function() {
                        clsArray.push(data[i][this] || this);
                    });
                    node.iconCls = clsArray.join(" ");
                    var texts = text.split(",");
                    var dispField = "";
                    for (var j = 0; j < texts.length; j++) {
                        dispField += (typeof data[i][texts[j]] == "undefined" ? "" : data[i][texts[j]]) + " ";
                    }
                    node.text = dispField || "";
                    var lvlNode = data[i][pid] || "";
                    var parentLvlNode = lvlNode.substring(0, lvlNode.length - lvlLength);
                    for (var j = 0; j < data.length; j++) {
                        var nodeId = data[j][id] || "";
                        var lvlNodeId = data[j][pid] || "";
                        if (lvlNodeId == parentLvlNode) {
                            node.pid = nodeId;
                            break;
                        }
                    }
                    node["dataSource"] = data[i];
                    if ("number" === conf.nodeDataType) {
                        node.id = Number(node.id);
                        node.pid = Number(node.pid);
                    }
                    nodeData.push(node);
                }
            } else if (treeType == "3") {
                data = kui.quickSort(data, pid);
                for (var i = 0; i < data.length; i++) {
                    var node = {};
                    //根据配置对id,pid做数据类型转换 @zhaozz 2015-11-17
                    data[i][id] = "number" === conf.nodeDataType ? Number(data[i][id]) + "" : data[i][id];
                    data[i][pid] = "number" === conf.nodeDataType ? Number(data[i][pid]) + "" : data[i][pid];
                    node.id = data[i][id] || "";
                    node.iconCls = node.iconCls || iconCls;
                    var texts = text.split(",");
                    var dispField = "";
                    for (var j = 0; j < texts.length; j++) {
                        dispField += (typeof data[i][texts[j]] == "undefined" ? "" : data[i][texts[j]]) + " ";
                    }
                    node.text = dispField || "";
                    var lvlNode = data[i][pid].replace("@", "") || "";
                    var parentLvlNode1 = lvlNode.substring(0, lvlNode.length - lvlLength);
                    for (var j = i; j >= 0; j--) {
                        var nodeId = data[j][id] || "";
                        var lvlNodeId = data[j][pid].replace("@", "") || "";
                        if (lvlNodeId == parentLvlNode1) {
                            node.pid = nodeId;
                            break;
                        }
                    }
                    if (!node.pid && lvlNode.length > 2) {
                        var parentLvlNode2 = lvlNode.substring(0, lvlNode.length - 2);
                        for (var j = i; j >= 0; j--) {
                            var nodeId = data[j][id] || "";
                            var lvlNodeId = data[j][pid].replace("@", "") || "";
                            if (lvlNodeId == parentLvlNode2) {
                                node.pid = nodeId;
                                break;
                            }
                        }
                    }
                    if ("number" === conf.nodeDataType) {
                        node.id = Number(node.id);
                        node.pid = Number(node.pid);
                    }
                    nodeData.push(node);
                }
            }
            return nodeData;
        }
    };
    /**
     * 初始化tree，添加tree样式。
     * @param target
     * @returns {*|jQuery|HTMLElement}
     */
    function wrapTree(target) {
        var tree = $(target);
        tree.addClass("tree");
        return tree;
    }
    /**
     * 得到静态目录，根据节点下面li元素构造tree的data。
     * @param target
     * @returns {Array}
     */
    function getContents(target) {
        var param = [];
        getContent(param, $(target));
        function getContent(aa, ul) {
            //一级目录
            ul.children("li").each(function() {
                var li = $(this);
                var options = $.extend({}, $.parser.parseOptions(this, [ "id", "iconCls", "state" ]), {
                    checked: li.attr("checked") ? true : undefined
                });
                options.text = li.children("span").html();
                if (!options.text) {
                    options.text = li.html();
                }
                //二级目录
                var subul = li.children("ul");
                if (subul.length) {
                    options.children = [];
                    //递归子目录
                    getContent(options.children, subul);
                }
                aa.push(options);
            });
        }
        return param;
    }
    function bindEvents(target) {
        bindTreeEvent(target, "dblclick");
        bindTreeEvent(target, "click");
        bindTreeEvent(target, "mouseover");
        bindTreeEvent(target, "mouseout");
        bindTreeEvent(target, "contextmenu");
        bindTreeEvent(target, "mousedown");
    }
    function bindTreeEvent(target, event) {
        _addEvent(target, event, function(e) {
            _treeEventHandler(e, event, target);
        });
    }
    function _addEvent(element, event, callback) {
        if (!callback || !element || !event) return false;
        var jqEvent = event + ".trees";
        $(element).unbind(jqEvent).bind(jqEvent, callback);
    }
    function _treeEventHandler(e, event, target) {
        var opts = $.data(target, "tree").options;
        var $tt = $(e.target);
        switch (event) {
          case "contextmenu":
            opts.onContextMenu.call(target, e, getNode(target, this));
            e.stopPropagation();
            break;

          case "mouseover":
            var $div = $tt.closest("div.tree-node");
            $div.addClass("tree-node-hover");
            if ($tt.hasClass("tree-hit")) {
                if ($tt.hasClass("tree-expanded")) {
                    $tt.addClass("tree-expanded-hover");
                } else {
                    $tt.addClass("tree-collapsed-hover");
                }
            }
            return;
            break;

          case "mouseout":
            var $div = $tt.closest("div.tree-node");
            $div.removeClass("tree-node-hover");
            if ($tt.hasClass("tree-hit")) {
                if ($tt.hasClass("tree-expanded")) {
                    $tt.removeClass("tree-expanded-hover");
                } else {
                    $tt.removeClass("tree-collapsed-hover");
                }
            }
            return;
            break;

          case "mousedown":
            if ($tt.hasClass("tree-hit") || $tt.hasClass("tree-checkbox")) return;
            break;

          case "click":
            if ($tt.hasClass("tree-hit")) {
                var $node = $tt.parent();
                toggle(target, $node[0]);
                return;
            } else if ($tt.hasClass("tree-checkbox")) {
                var $node = $tt.parent();
                setCheckbox1(target, $node[0], !$tt.hasClass("tree-checkbox1"));
                return;
            } else {
                var $node = $tt.closest("div.tree-node");
                onSelect(target, $node[0]);
                //@zhaozz 2016-06-02 点击文字，同时要勾选对应面的checkbox 
                if ($tt.prev().hasClass("tree-checkbox")) {
                    setCheckbox1(target, $tt.parent()[0], !$tt.prev().hasClass("tree-checkbox1"));
                    return;
                }
                opts.onClick.call(target, getSelected(target));
                e.stopPropagation();
            }
            break;

          case "dblclick":
            var $node = $tt.closest("div.tree-node");
            onSelect(target, $node);
            opts.onDblClick.call(target, getSelected(target));
            e.stopPropagation();
            break;

          default:
            break;
        }
    }
    //拖动失效
    function draDisable(target) {
        var node = $(target).find("div.tree-node");
        node.draggable("disable");
        node.css("cursor", "pointer");
    }
    //允许拖动文件
    function dragFile(target) {
        var opts = $.data(target, "tree").options;
        var tree = $.data(target, "tree").tree;
        if (opts.dragLeafOnly) {
            tree.find("div.tree-node").filter(function() {
                return $(this).next("ul").length == 0;
            }).draggable({
                disabled: false,
                revert: true,
                cursor: opts.cursor || "pointer",
                proxy: opts.proxy || function(target) {
                    var p = $('<div class="tree-node-proxy tree-dnd-no"></div>').appendTo("body");
                    p.html($(target).find(".tree-title").html());
                    p.hide();
                    return p;
                },
                deltaX: 15,
                deltaY: 15,
                onBeforeDrag: function(e) {
                    if (e.which != 1) {
                        return false;
                    }
                    opts.onBeforeDrag.call(target, e);
                },
                onStartDrag: function(e) {
                    $(this).draggable("proxy").css({
                        left: -1e4,
                        top: -1e4
                    });
                    opts.onStartDrag.call(target, e);
                },
                onDrag: function(e) {
                    var x1 = e.pageX, y1 = e.pageY, x2 = e.data.startX, y2 = e.data.startY;
                    var d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
                    if (d > 3) {
                        $(this).draggable("proxy").show();
                    }
                    this.pageY = e.pageY;
                    opts.onDrag.call(target, e);
                },
                onStopDrag: function(e) {
                    opts.onStopDrag.call(target, e);
                },
                onRevert: function(e) {
                    opts.onRevert.call(target, e);
                }
            });
            return;
        }
        tree.find("div.tree-node").draggable({
            disabled: false,
            revert: true,
            cursor: "pointer",
            proxy: function(target) {
                var p = $('<div class="tree-node-proxy tree-dnd-no"></div>').appendTo("body");
                p.html($(target).find(".tree-title").html());
                p.hide();
                return p;
            },
            deltaX: 15,
            deltaY: 15,
            onBeforeDrag: function(e) {
                if (e.which != 1) {
                    return false;
                }
                $(this).next("ul").find("div.tree-node").droppable({
                    accept: "no-accept"
                });
                var indents = $(this).find("span.tree-indent");
                if (indents.length) {
                    e.data.startLeft += indents.length * indents.width();
                }
            },
            onStartDrag: function() {
                $(this).draggable("proxy").css({
                    left: -1e4,
                    top: -1e4
                });
            },
            onDrag: function(e) {
                var x1 = e.pageX, y1 = e.pageY, x2 = e.data.startX, y2 = e.data.startY;
                var d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
                if (d > 3) {
                    $(this).draggable("proxy").show();
                }
                this.pageY = e.pageY;
            },
            onStopDrag: function(e) {
                $(this).next("ul").find("div.tree-node").droppable({
                    accept: "div.tree-node"
                });
            }
        }).droppable({
            accept: "div.tree-node",
            onDragOver: function(e, _1b) {
                var y = _1b.pageY;
                var top = $(this).offset().top;
                var outheight = top + $(this).outerHeight();
                $(_1b).draggable("proxy").removeClass("tree-dnd-no").addClass("tree-dnd-yes");
                $(this).removeClass("tree-node-append tree-node-top tree-node-bottom");
                if (y > top + (outheight - top) / 2) {
                    if (outheight - y < 5) {
                        $(this).addClass("tree-node-bottom");
                    } else {
                        $(this).addClass("tree-node-append");
                    }
                } else {
                    if (y - top < 5) {
                        $(this).addClass("tree-node-top");
                    } else {
                        $(this).addClass("tree-node-append");
                    }
                }
            },
            onDragLeave: function(e, _1e) {
                $(_1e).draggable("proxy").removeClass("tree-dnd-yes").addClass("tree-dnd-no");
                $(this).removeClass("tree-node-append tree-node-top tree-node-bottom");
            },
            onDrop: function(e, _target) {
                var t = this;
                var handler, position;
                if ($(this).hasClass("tree-node-append")) {
                    handler = dragIn;
                    position = "append";
                } else {
                    handler = dragOut;
                    position = $(this).hasClass("tree-node-top") ? "top" : "bottom";
                }
                if (opts.dropMethod) {
                    var dropMethod = eval(opts.dropMethod);
                    dropMethod.call(target, _target, t, position, function() {
                        setTimeout(function() {
                            handler(_target, t, position);
                        }, 0);
                    });
                } else {
                    setTimeout(function() {
                        handler(_target, t, position);
                    }, 0);
                }
                $(this).removeClass("tree-node-append tree-node-top tree-node-bottom");
            }
        });
        function dragIn(_target, div) {
            if (getNode(target, div).state == "closed") {
                expand(target, div, function() {
                    _eventHandler();
                });
            } else {
                _eventHandler();
            }
            function _eventHandler() {
                var param = $(target).tree("pop", _target);
                $(target).tree("append", {
                    parent: div,
                    data: [ param ]
                });
                opts.onDrop.call(target, div, param, "append");
            }
        }
        function dragOut(_29, _2a, _2b) {
            var _2c = {};
            if (_2b == "top") {
                _2c.before = _2a;
            } else {
                _2c.after = _2a;
            }
            var param = $(target).tree("pop", _29);
            _2c.data = param;
            $(target).tree("insert", _2c);
            opts.onDrop.call(target, _2a, param, _2b);
        }
    }
    //设置选中状态
    function setCheckbox1(target, div, state) {
        var opts = $.data(target, "tree").options;
        if (!opts.checkbox) {
            return;
        }
        var d = $(div);
        var ck = d.find(".tree-checkbox");
        ck.removeClass("tree-checkbox0 tree-checkbox1 tree-checkbox2");
        if (state) {
            ck.addClass("tree-checkbox1");
        } else {
            ck.addClass("tree-checkbox0");
        }
        if ("true" == String(opts.cascadeCheck)) {
            setCascadeCheck(d);
            setChildCheckbox(d);
        }
        var node = getNode(target, div);
        opts.onCheck.call(target, node, state);
        function setChildCheckbox(target) {
            var checknode = target.next().find(".tree-checkbox");
            checknode.removeClass("tree-checkbox0 tree-checkbox1 tree-checkbox2");
            if (target.find(".tree-checkbox").hasClass("tree-checkbox1")) {
                checknode.addClass("tree-checkbox1");
            } else {
                checknode.addClass("tree-checkbox0");
            }
        }
        //设置级联选中
        function setCascadeCheck(d) {
            var parent = getParent(target, d[0]);
            if (parent) {
                var ck = $(parent.target).find(".tree-checkbox");
                ck.removeClass("tree-checkbox0 tree-checkbox1 tree-checkbox2");
                if (isAllSelected(d)) {
                    ck.addClass("tree-checkbox1");
                } else {
                    if (isCheckbox0(d)) {
                        ck.addClass("tree-checkbox0");
                    } else {
                        if (opts.cascadeCheckValue && opts.cascadeCheck === true) ck.addClass("tree-checkbox1"); else ck.addClass("tree-checkbox2");
                    }
                }
                setCascadeCheck($(parent.target));
            }
            function isAllSelected(n) {
                var ck = n.find(".tree-checkbox");
                if (ck.hasClass("tree-checkbox0") || ck.hasClass("tree-checkbox2")) {
                    return false;
                }
                var b = true;
                n.parent().siblings().each(function() {
                    if (!$(this).children("div.tree-node").children(".tree-checkbox").hasClass("tree-checkbox1")) {
                        b = false;
                    }
                });
                return b;
            }
            //节点未选中
            function isCheckbox0(n) {
                var ck = n.find(".tree-checkbox");
                if (ck.hasClass("tree-checkbox1") || ck.hasClass("tree-checkbox2")) {
                    return false;
                }
                var b = true;
                n.parent().siblings().each(function() {
                    if (!$(this).children("div.tree-node").children(".tree-checkbox").hasClass("tree-checkbox0")) {
                        b = false;
                    }
                });
                return b;
            }
        }
    }
    function setCheckboxNoCascadeCheck(target, div, state) {
        var opts = $.data(target, "tree").options;
        if (!opts.checkbox) {
            return;
        }
        var d = $(div);
        var ck = d.find(".tree-checkbox");
        ck.removeClass("tree-checkbox0 tree-checkbox1 tree-checkbox2");
        if (state) {
            ck.addClass("tree-checkbox1");
        } else {
            ck.addClass("tree-checkbox0");
        }
        //if (opts.cascadeCheck) {
        // setCascadeCheck(d);
        // setChildCheckbox(d);
        //    }
        var node = getNode(target, div);
        opts.onCheck.call(target, node, state);
        function setChildCheckbox(target) {
            var checknode = target.next().find(".tree-checkbox");
            checknode.removeClass("tree-checkbox0 tree-checkbox1 tree-checkbox2");
            if (target.find(".tree-checkbox").hasClass("tree-checkbox1")) {
                checknode.addClass("tree-checkbox1");
            } else {
                checknode.addClass("tree-checkbox0");
            }
        }
        //设置级联选中
        function setCascadeCheck(d) {
            var parent = getParent(target, d[0]);
            if (parent) {
                var ck = $(parent.target).find(".tree-checkbox");
                ck.removeClass("tree-checkbox0 tree-checkbox1 tree-checkbox2");
                if (isAllSelected(d)) {
                    ck.addClass("tree-checkbox1");
                } else {
                    if (isCheckbox0(d)) {
                        ck.addClass("tree-checkbox0");
                    } else {
                        if (opts.cascadeCheckValue && opts.cascadeCheck === true) ck.addClass("tree-checkbox1"); else ck.addClass("tree-checkbox2");
                    }
                }
                setCascadeCheck($(parent.target));
            }
            function isAllSelected(n) {
                var ck = n.find(".tree-checkbox");
                if (ck.hasClass("tree-checkbox0") || ck.hasClass("tree-checkbox2")) {
                    return false;
                }
                var b = true;
                n.parent().siblings().each(function() {
                    if (!$(this).children("div.tree-node").children(".tree-checkbox").hasClass("tree-checkbox1")) {
                        b = false;
                    }
                });
                return b;
            }
            //节点未选中
            function isCheckbox0(n) {
                var ck = n.find(".tree-checkbox");
                if (ck.hasClass("tree-checkbox1") || ck.hasClass("tree-checkbox2")) {
                    return false;
                }
                var b = true;
                n.parent().siblings().each(function() {
                    if (!$(this).children("div.tree-node").children(".tree-checkbox").hasClass("tree-checkbox0")) {
                        b = false;
                    }
                });
                return b;
            }
        }
    }
    function setCheckbox(target, div) {
        var opts = $.data(target, "tree").options;
        var d = $(div);
        if (getHitNode(target, div)) {
            var ck = d.find(".tree-checkbox");
            if (ck.length) {
                if (ck.hasClass("tree-checkbox1")) {
                    setCheckbox1(target, div, true);
                } else {
                    setCheckbox1(target, div, false);
                }
            } else {
                if (opts.onlyLeafCheck) {
                    $('<span class="tree-checkbox tree-checkbox0"></span>').insertBefore(d.find(".tree-title"));
                    bindEvents(target);
                }
            }
        } else {
            var ck = d.find(".tree-checkbox");
            if (opts.onlyLeafCheck) {
                ck.remove();
            } else {
                if (ck.hasClass("tree-checkbox1")) {
                    setCheckbox1(target, div, true);
                } else {
                    if (ck.hasClass("tree-checkbox2")) {
                        var _44 = true;
                        var _45 = true;
                        var children = getChildren(target, div);
                        for (var i = 0; i < children.length; i++) {
                            if (children[i].checked) {
                                _45 = false;
                            } else {
                                _44 = false;
                            }
                        }
                        if (_44) {
                            setCheckbox1(target, div, true);
                        }
                        if (_45) {
                            setCheckbox1(target, div, false);
                        }
                    }
                }
            }
        }
    }
    //删除已经追加的树节点：nodeId,减少函数：childrensObject中递归次数
    function deleteContent(nodeId, contents) {
        var childrens = {};
        //没有nodeId的时候由于不能唯一确定node的id所以不能确定该节点是否唯一
        if (!nodeId) {
            return contents;
        }
        var array = [];
        for (var i = 0; i < contents.length; i++) {
            var node = contents[i];
            var id = node.id;
            if (nodeId != id) {
                array.push(node);
            }
        }
        return array;
    }
    //统计节点是否有子节点，如果返回数据中有：leaf:true这种的话（是否是叶子节点）就更好，不用在这里统计了
    function childrensObject(nodeId, contents) {
        var childrens = {};
        if (!contents) {
            return childrens;
        }
        var array = [];
        for (var i = 0; i < contents.length; i++) {
            var node = contents[i];
            var pid = node.pid;
            if (nodeId == pid) {
                array.push(node);
            }
        }
        childrens.children = array;
        return childrens;
    }
    //加载树
    /**
     * tree的数据加载
     * @param target tree节点对象
     * @param ul 需要添加的节点
     * @param data 数据源 array类型
     * @param isAppend 是否是增加，tree代表增加，ul下的元素不清空，
     */
    function loadData(target, ul, data, isAppend) {
        data = data || [];
        var opts = $.data(target, "tree").options;
        formatDataByConf(opts);
        if (!$.isEmptyObject(opts.conf)) {
            data = opts.makeTreeData.call(this, opts.conf, data);
        }
        data = opts.loadFilter.call(target, data, $(ul).prev("div.tree-node")[0]);
        if (!isAppend) {
            $(ul).empty();
            opts.data = data || {};
        }
        //console.log("tree节点总数"+data.length);
        //console.time("newRenderNodes");
        newRenderNodes();
        //console.timeEnd("newRenderNodes");
        bindEvents(target);
        if (opts.dnd) {
            dragFile(target);
        }
        setTimeout(function() {
            createTreeStyle(target, target);
        }, 0);
        var dragNode = null;
        if (target != ul) {
            var prev = $(ul).prev();
            dragNode = getNode(target, prev[0]);
        }
        opts.onLoadSuccess.call(target, dragNode, data);
        function newRenderNodes() {
            var treeObj = {
                rootNodeIdArr: []
            };
            //遍历第一遍data对象，构造数据格式，并且去重。
            for (var i = 0; i < data.length; i++) {
                if (treeObj[data[i].id]) {
                    //数据去重，按照id去重
                    data.splice(i, 1);
                    i--;
                } else {
                    treeObj[data[i].id] = {
                        node: data[i],
                        pNode: data[i].pid,
                        children: []
                    };
                }
            }
            //遍历第二遍，完善数据格式，保存节点之间的父子关系
            for (var i = 0; i < data.length; i++) {
                if (treeObj[data[i].pid]) {
                    treeObj[data[i].pid].children.push(data[i].id);
                } else {
                    treeObj.rootNodeIdArr.push(data[i].id);
                }
            }
            var lvlLen = $(ul).prev("div.tree-node").find("span.tree-indent, span.tree-hit").length, //层级标志
            checkNodeIdArr = [], //存在checkbox时，选中的nodeId
            treeHtmlArr = [];
            //存放拼接的html
            if (treeObj.rootNodeIdArr.length == 0) {
                console.log("tree加载失败，出现了循环节点，没有根节点出现！");
                //数据有问题，不初始化树
                return false;
            }
            parseTreeNode(treeObj.rootNodeIdArr, lvlLen);
            function parseTreeNode(nodeIdArr, _len) {
                for (var i = 0; i < nodeIdArr.length; i++) {
                    treeHtmlArr.push("<li>");
                    var treeNode = treeObj[nodeIdArr[i]], node = treeNode.node, //data中的原始节点
                    children = treeNode.children || [], display = "none";
                    //存在子节点时，子节点是否隐藏，默认为none-隐藏
                    treeHtmlArr.push("<div class='tree-node' node-id='" + node.id + "'>");
                    for (var j = 0; j < _len; j++) {
                        treeHtmlArr.push("<span class='tree-indent'></span>");
                    }
                    if (children.length == 0) {
                        //叶子节点
                        treeHtmlArr.push('<span class="tree-indent"></span>');
                        treeHtmlArr.push('<span class="tree-icon tree-file ' + (node.iconCls ? node.iconCls : "") + '"></span>');
                    } else {
                        if (opts.treeExpand == "all" || opts.treeExpand > _len) {
                            treeHtmlArr.push('<span class="tree-hit tree-expanded"></span>');
                            treeHtmlArr.push('<span class="tree-icon tree-folder tree-folder-open ' + (node.iconCls ? node.iconCls : "") + '"></span>');
                            display = "block";
                        } else {
                            treeHtmlArr.push('<span class="tree-hit tree-collapsed"></span>');
                            treeHtmlArr.push('<span class="tree-icon tree-folder ' + (node.iconCls ? node.iconCls : "") + '"></span>');
                        }
                    }
                    //存在多选框
                    if (eval(opts.checkbox)) {
                        if (!opts.onlyLeafCheck || opts.onlyLeafCheck && treeNode.children.length == 0) {
                            if (node.checked) {
                                treeHtmlArr.push('<span class="tree-checkbox tree-checkbox1"></span>');
                                checkNodeIdArr.push(node.id);
                            } else {
                                treeHtmlArr.push('<span class="tree-checkbox tree-checkbox0"></span>');
                            }
                        }
                    }
                    var txt = node.text ? node.text : $(node.target).find("span.tree-title").text();
                    treeHtmlArr.push("<span class='tree-title' title='" + txt + "'>" + txt + "</span>");
                    treeHtmlArr.push("</div>");
                    if (children.length > 0) {
                        treeHtmlArr.push("<ul style='display: " + display + "'>");
                        parseTreeNode(children, _len + 1);
                        treeHtmlArr.push("</ul>");
                    }
                    treeHtmlArr.push("</li>");
                }
            }
            $(ul).append(treeHtmlArr.join(""));
            for (var i = 0; i < checkNodeIdArr.length; i++) {
                var $div = $("div.tree-node[node-id=" + checkNodeIdArr[i] + "]", $(target));
                setCheckbox1(target, $div[0], true);
            }
        }
    }
    function createTreeStyle(target, ul, recursion) {
        var opts = $.data(target, "tree").options;
        if (!opts.lines) {
            return;
        }
        if (!recursion) {
            recursion = true;
            $(target).find("span.tree-indent").removeClass("tree-line tree-join tree-joinbottom");
            $(target).find("div.tree-node").removeClass("tree-node-last tree-root-first tree-root-one");
            var roots = $(target).tree("getRoots");
            if (roots.length > 1) {
                $(roots[0].target).addClass("tree-root-first");
            } else if (roots.length != 0) {
                $(roots[0].target).addClass("tree-root-one");
            }
        }
        $(ul).children("li").each(function() {
            var $node = $(this).children("div.tree-node");
            var ul = $node.next("ul");
            if (ul.length) {
                if ($(this).next().length) {
                    addLine($node);
                }
                createTreeStyle(target, ul, recursion);
            } else {
                addIcon($node);
            }
        });
        var lastNode = $(ul).children("li:last").children("div.tree-node").addClass("tree-node-last");
        lastNode.children("span.tree-join").removeClass("tree-join").addClass("tree-joinbottom");
        function addIcon(node, _61) {
            var icon = node.find("span.tree-icon");
            icon.prev("span.tree-indent").addClass("tree-join");
        }
        function addLine(node) {
            var spanL = node.find("span.tree-indent, span.tree-hit").length;
            node.next().find("div.tree-node").each(function() {
                $(this).children("span:eq(" + (spanL - 1) + ")").addClass("tree-line");
            });
        }
    }
    function getNodeData(nodeId, opts) {
        if (nodeId) {
            var data = opts.data;
            if (data) {
                for (var i = data.length - 1; i >= 0; i--) {
                    if (nodeId == data[i].id) return data[i];
                }
            }
        }
        return {};
    }
    /**
     * ajax请求加载数据
     * @param target
     * @param ul
     * @param options
     * @param _68
     */
    function request(target, ul, options, _68) {
        var opts = $.data(target, "tree").options;
        if ((!opts.req || opts.req.length == 0) && !opts.url) {
            return false;
        }
        options = options || {};
        var param = null;
        if (target != ul) {
            var div = $(ul).prev();
            param = getNode(target, div[0]);
        }
        if (opts.onBeforeLoad.call(target, param, options) == false) {
            return;
        }
        var floder = $("<span class='tree-folder tree-loading'></span>");
        $(target).append(floder);
        var state = opts.loader.call(target, options, function(data) {
            floder.remove();
            if (data) {
                opts.base = opts.data = data;
                //                    if(!$.isEmptyObject(opts.conf)){
                //                        opts.data = opts.makeTreeData.call(this, $.extend({},opts,opts.conf),opts.data);
                //                    }
                loadData(target, ul, opts.data);
            }
            if (_68) {
                _68();
            }
        }, function() {
            floder.remove();
            opts.onLoadError.apply(target, arguments);
            if (_68) {
                _68();
            }
        });
        if (state == false) {
            floder.remove();
        }
    }
    //打开节点
    function expand(target, u, _72) {
        var opts = $.data(target, "tree").options;
        var hit = $(u).children("span.tree-hit");
        if (hit.length == 0) {
            return;
        }
        if (hit.hasClass("tree-expanded")) {
            return;
        }
        var param = getNode(target, u);
        if (opts.onBeforeExpand.call(target, param) == false) {
            return;
        }
        hit.removeClass("tree-collapsed tree-collapsed-hover").addClass("tree-expanded");
        hit.next().addClass("tree-folder-open");
        var ul = $(u).next();
        if (ul.length) {
            if (opts.animate) {
                ul.slideDown(opts.animateSpeed, function() {
                    opts.onExpand.call(target, param);
                    if (_72) {
                        _72();
                    }
                });
            } else {
                ul.css("display", "block");
                opts.onExpand.call(target, param);
                if (_72) {
                    _72();
                }
            }
        } else {
            var dul = $('<ul style="display:none"></ul>').insertAfter(u);
            request(target, dul[0], {
                id: param.id
            }, function() {
                if (dul.is(":empty")) {
                    dul.remove();
                }
                if (opts.animate) {
                    dul.slideDown(opts.animateSpeed, function() {
                        opts.onExpand.call(target, param);
                        if (_72) {
                            _72();
                        }
                    });
                } else {
                    dul.css("display", "block");
                    opts.onExpand.call(target, param);
                    if (_72) {
                        _72();
                    }
                }
            });
        }
    }
    //关闭节点
    function collapse(target, ul) {
        var opts = $.data(target, "tree").options;
        var hit = $(ul).children("span.tree-hit");
        if (hit.length == 0) {
            return;
        }
        if (hit.hasClass("tree-collapsed")) {
            return;
        }
        var param = getNode(target, ul);
        if (opts.onBeforeCollapse.call(target, param) == false) {
            return;
        }
        hit.removeClass("tree-expanded tree-expanded-hover").addClass("tree-collapsed");
        hit.next().removeClass("tree-folder-open");
        var ul = $(ul).next();
        if (opts.animate) {
            ul.slideUp(opts.animateSpeed, function() {
                opts.onCollapse.call(target, param);
            });
        } else {
            ul.css("display", "none");
            opts.onCollapse.call(target, param);
        }
    }
    //打开或关闭节点的触发器
    function toggle(_7c, _7d) {
        var hit = $(_7d).children("span.tree-hit");
        if (hit.length == 0) {
            return;
        }
        if (hit.hasClass("tree-expanded")) {
            collapse(_7c, _7d);
        } else {
            expand(_7c, _7d);
        }
    }
    //打开所有的节点
    function expandAll(_7f, _80) {
        var _81 = getChildren(_7f, _80);
        if (_80) {
            _81.unshift(getNode(_7f, _80));
        }
        for (var i = 0; i < _81.length; i++) {
            expand(_7f, _81[i].target);
        }
    }
    //打开从根节点到指定节点之间的所有节点
    function expandTo(target, node) {
        if (!$(node).length) {
            return;
        }
        var param = [];
        var p = getParent(target, node);
        while (p) {
            param.unshift(p);
            p = getParent(target, p.target);
        }
        for (var i = 0; i < param.length; i++) {
            expand(target, param[i].target);
        }
    }
    //关闭所有的节点
    function collapseAll(target, node) {
        var children = getChildren(target, node);
        if (node) {
            children.unshift(getNode(target, node));
        }
        for (var i = 0; i < children.length; i++) {
            collapse(target, children[i].target);
        }
    }
    //获取根节点，返回节点对象
    function getRoot(target) {
        var param = getRoots(target);
        if (param.length) {
            return param[0];
        } else {
            return null;
        }
    }
    //获取根节点，返回节点数组
    function getRoots(target) {
        var param = [];
        $(target).children("li").each(function() {
            var node = $(this).children("div.tree-node");
            param.push(getNode(target, node[0]));
        });
        return param;
    }
    //获取子节点
    function getChildren(target, _93) {
        var param = [];
        if (_93) {
            _95($(_93));
        } else {
            var roots = getRoots(target);
            for (var i = 0; i < roots.length; i++) {
                param.push(roots[i]);
                _95($(roots[i].target));
            }
        }
        function _95(t) {
            t.next().find("div.tree-node").each(function() {
                param.push(getNode(target, this));
            });
        }
        return param;
    }
    //获取父节点
    function getParent(target, _99) {
        var ul = $(_99).parent().parent();
        if (ul[0] == target) {
            return null;
        } else {
            return getNode(target, ul.prev()[0]);
        }
    }
    //获取所有被选择的节点
    function getChecked(target) {
        var param = [];
        $(target).find(".tree-checkbox1").each(function() {
            var parent = $(this).parent();
            param.push(getNode(target, parent[0]));
        });
        return param;
    }
    function getCascadeCheckNode(target) {
        var param = [];
        $(target).find(".tree-checkbox2").each(function() {
            var parent = $(this).parent();
            param.push(getNode(target, parent[0]));
        });
        $(target).find(".tree-checkbox1").each(function() {
            var parent = $(this).parent();
            param.push(getNode(target, parent[0]));
        });
        return param;
    }
    //获取被选择的节点并返回，如果没有节点被选择则返回null
    function getSelected(target) {
        var selected = $(target).find("div.tree-node-selected");
        if (selected.length) {
            return getNode(target, selected[0]);
        } else {
            return null;
        }
    }
    function getSelectedBase(target) {
        var selected = $(target).find("div.tree-node-selected"), opts = $.data(target, "tree").options, conf = $.extend({}, opts, opts.conf);
        base = opts.base;
        if (selected.length && base) {
            var node = getNode(target, selected[0]);
            var nodeId = conf.nodeId || "id";
            for (var i = 0, len = base.length; i < len; i++) {
                if (base[i][nodeId] == node.id) return base[i];
            }
        } else {
            return null;
        }
    }
    //添加若干子节点到一个父节点
    function append(target, options) {
        var parent = $(options.parent);
        var ul;
        if (parent.length == 0) {
            ul = $(target);
        } else {
            ul = parent.next();
            if (ul.length == 0) {
                ul = $("<ul></ul>").insertAfter(parent);
            }
        }
        if (options.data && options.data.length) {
            var icon = parent.find("span.tree-icon");
            if (icon.hasClass("tree-file")) {
                icon.removeClass("tree-file").addClass("tree-folder");
                var hit = $('<span class="tree-hit tree-expanded"></span>').insertBefore(icon);
                if (hit.prev().length) {
                    hit.prev().remove();
                }
            }
        }
        loadData(target, ul[0], options.data, true);
        setCheckbox(target, ul.prev());
    }
    function insert(target, _a8) {
        var ref = _a8.before || _a8.after;
        var _a9 = getParent(target, ref);
        var li;
        if (_a9) {
            append(target, {
                parent: _a9.target,
                data: [ _a8.data ]
            });
            li = $(_a9.target).next().children("li:last");
        } else {
            append(target, {
                parent: null,
                data: [ _a8.data ]
            });
            li = $(target).children("li:last");
        }
        if (_a8.before) {
            li.insertBefore($(ref).parent());
        } else {
            li.insertAfter($(ref).parent());
        }
    }
    function remove(_ab, _ac) {
        var _ad = getParent(_ab, _ac);
        var _ae = $(_ac);
        var li = _ae.parent();
        var ul = li.parent();
        li.remove();
        if (ul.children("li").length == 0) {
            var _ae = ul.prev();
            _ae.find(".tree-icon").removeClass("tree-folder").addClass("tree-file");
            _ae.find(".tree-hit").remove();
            $('<span class="tree-indent"></span>').prependTo(_ae);
            if (ul[0] != _ab) {
                ul.remove();
            }
        }
        if (_ad) {
            setCheckbox(_ab, _ad.target);
        }
        createTreeStyle(_ab, _ab);
    }
    //获取特定的节点数据，包括它的子节点。
    function getData(target, _b1) {
        function getSubNode(aa, ul) {
            ul.children("li").each(function() {
                var node = $(this).children("div.tree-node");
                var n = getNode(target, node[0]);
                var sub = $(this).children("ul");
                if (sub.length) {
                    n.children = [];
                    getSubNode(n.children, sub);
                }
                aa.push(n);
            });
        }
        if (_b1) {
            var opts = getNode(target, _b1);
            opts.children = [];
            getSubNode(opts.children, $(_b1).next());
            return opts;
        } else {
            return null;
        }
    }
    function update(target, params) {
        var _b9 = $(params.target);
        var node = getNode(target, params.target);
        if (node.iconCls) {
            _b9.find(".tree-icon").removeClass(node.iconCls);
        }
        var opts = $.extend({}, node, params);
        $.data(params.target, "tree-node", opts);
        _b9.attr("node-id", opts.id);
        _b9.find(".tree-title").html(opts.text);
        if (opts.iconCls) {
            _b9.find(".tree-icon").addClass(opts.iconCls);
        }
        if (node.checked != opts.checked) {
            setCheckbox1(target, params.target, opts.checked);
        }
    }
    //获取特定的节点对象。
    function getNode(target, div) {
        var opts = $.data(target, "tree").options;
        var nodeid = undefined, data = undefined;
        if (div && div.getAttribute) {
            nodeid = div.getAttribute("node-id");
        }
        var data = $.extend({}, getNodeData(nodeid, opts), {
            target: div,
            checked: $(div).find(".tree-checkbox").hasClass("tree-checkbox1")
        });
        if (!getHitNode(target, div)) {
            data.state = $(div).find(".tree-hit").hasClass("tree-expanded") ? "open" : "closed";
        }
        return data;
    }
    //查找指定的节点并返回节点对象
    function find(target, id) {
        //zhubc20160513 jquery的元素查找，按属性查找时，需要属性值加上引号''
        var node = $(target).find("div.tree-node[node-id='" + id + "']");
        if (node.length) {
            return getNode(target, node[0]);
        } else {
            return null;
        }
    }
    function onSelect(target, node) {
        var opts = $.data(target, "tree").options;
        var $node = getNode(target, node);
        if (opts.onBeforeSelect.call(target, $node) == false) {
            return;
        }
        $("div.tree-node-selected", target).removeClass("tree-node-selected");
        $(node).addClass("tree-node-selected");
        opts.onSelect.call(target, $node);
    }
    //判断该节点下是否有含有子节点的节点
    function getHitNode(_c7, target) {
        var ul = $(target);
        var hit = ul.children("span.tree-hit");
        return hit.length == 0;
    }
    //开始编辑节点
    function beginEdit(target, node) {
        var opts = $.data(target, "tree").options;
        var _ce = getNode(target, node);
        if (opts.onBeforeEdit.call(target, _ce) == false) {
            return;
        }
        $(node).css("position", "relative");
        var nt = $(node).find(".tree-title");
        var _cf = nt.outerWidth();
        nt.empty();
        var _d0 = $('<input class="tree-editor">').appendTo(nt);
        _d0.val(_ce.text).focus();
        _d0.width(_cf + 20);
        _d0.height(document.compatMode == "CSS1Compat" ? 18 - (_d0.outerHeight() - _d0.height()) : 18);
        _d0.bind("click", function(e) {
            return false;
        }).bind("mousedown", function(e) {
            e.stopPropagation();
        }).bind("mousemove", function(e) {
            e.stopPropagation();
        }).bind("keydown", function(e) {
            if (e.keyCode == 13) {
                endEdit(target, node);
                return false;
            } else {
                if (e.keyCode == 27) {
                    cancelEdit(target, node);
                    return false;
                }
            }
        }).bind("blur", function(e) {
            e.stopPropagation();
            endEdit(target, node);
        });
    }
    //结束编辑节点
    function endEdit(target, node) {
        var opts = $.data(target, "tree").options;
        $(node).css("position", "");
        var editor = $(node).find("input.tree-editor");
        var val = editor.val();
        editor.remove();
        var param = getNode(target, node);
        param.text = val;
        update(target, param);
        opts.onAfterEdit.call(target, param);
    }
    //取消编辑节点
    function cancelEdit(target, node) {
        var opts = $.data(target, "tree").options;
        $(node).css("position", "");
        $(node).find("input.tree-editor").remove();
        var param = getNode(target, node);
        update(target, param);
        opts.onCancelEdit.call(target, param);
    }
    /**
     * 根据conf里的配置 格式化 data字段
     * @param  {[type]} opts [description]
     * @return {[type]}      [description]
     */
    function formatDataByConf(opts) {
        var conf = opts.col && opts.col.editor ? opts.col.editor.options : null;
        //conf 配置转换       
        if (opts.data && conf) {
            $.each(opts.data, function(index, d) {
                var arrNodeName = [];
                d.pid = d[conf.parNode] || d.pid;
                d.id = d[conf.nodeId] || d.id;
                conf.nodeName && $.each(conf.nodeName.split(/,/g), function(index1, name) {
                    arrNodeName.push(d[name]);
                });
                d.text = arrNodeName.length > 0 ? arrNodeName.join(" ") : d.text;
            });
        }
    }
    /**
     * checkbox全选中
     * @param target
     */
    function checkedAll(target) {
        var $checkArr = $(target).find(".tree-checkbox");
        if ($checkArr.length > 0) {
            $checkArr.removeClass("tree-checkbox0 tree-checkbox1 tree-checkbox2").addClass("tree-checkbox1");
        }
    }
    /**
     * checkbox全不选
     * @param target
     */
    function unCheckedAll(target) {
        var $checkArr = $(target).find(".tree-checkbox");
        if ($checkArr.length > 0) {
            $checkArr.removeClass("tree-checkbox0 tree-checkbox1 tree-checkbox2").addClass("tree-checkbox0");
        }
    }
    /**
     * checkbox反选
     * @param target
     */
    function unChecked(target) {
        var $checkArr = $(target).find(".tree-checkbox");
        for (var i = 0; i < $checkArr.length; i++) {
            var $check = $($checkArr[i]);
            if ($check.hasClass("tree-checkbox0")) {
                $check.removeClass("tree-checkbox0").addClass("tree-checkbox1");
            } else if ($check.hasClass("tree-checkbox1")) {
                $check.removeClass("tree-checkbox1").addClass("tree-checkbox0");
            }
        }
    }
});
