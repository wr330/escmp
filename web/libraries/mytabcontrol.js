var MyTabControl = $extend(dorado.widget.TabControl, {
        constructor: function() {
            $invokeSuper.call(this, arguments);
            this._createMaximizeButton();
        },
    
        maximizeRestore: function() {
            var tabcontrol = this, dom = tabcontrol._dom, doms = tabcontrol._doms;
            if (dom) {
                if (tabcontrol._maximized) {
    
                    $fly(dom).unfullWindow({
                        callback: function() {
                            tabcontrol._maximized = false;
                            tabcontrol._width = tabcontrol._originalWidth;
                            tabcontrol._height = tabcontrol._originalHeight;
                            tabcontrol._realWidth = tabcontrol._originalRealWidth;
                            tabcontrol._realHeight = tabcontrol._originalRealHeight;
                            tabcontrol._left = tabcontrol._originalLeft;
                            tabcontrol._top = tabcontrol._originalTop;
                            tabcontrol.refresh();
                        }
                    });
    
                    var maxmizeButton = tabcontrol._maximizeButton;
                    if (maxmizeButton) {
                        $fly(maxmizeButton._dom).prop("className", "d-maximize-button");
                        maxmizeButton._className = "d-maximize-button";
                    }
                }
            }
        },
    
        maximize: function() {
            var tabcontrol = this, dom = tabcontrol._dom;
            if (dom) {
                tabcontrol._originalWidth = tabcontrol._width;
                tabcontrol._originalHeight = tabcontrol._height;
                tabcontrol._originalRealWidth = tabcontrol._realWidth;
                tabcontrol._originalRealHeight = tabcontrol._realHeight;
                tabcontrol._originalLeft = tabcontrol._left;
                tabcontrol._originalTop = tabcontrol._top;
    
                var maxmizeButton = tabcontrol._maximizeButton;
                if (maxmizeButton) {
                    $fly(maxmizeButton._dom).prop("className", "d-restore-button");
                    maxmizeButton._className = "d-restore-button";
                }
    
                $fly(dom).fullWindow({
                    modifySize: false,
                    callback: function(docSize) {
                        tabcontrol._maximized = true;
                        tabcontrol._width = docSize.width;
                        tabcontrol._height = docSize.height;
                        tabcontrol._realWidth = tabcontrol._realHeight = undefined;
                        tabcontrol.refresh();
                    }
                });
            }
        },
    
        _createMaximizeButton: function() {
            var tabcontrol = this;
            var button = new dorado.widget.SimpleButton({
                id: tabcontrol._id + "_maximize",
                className: "d-maximize-button",
                listener: {
                    onClick: function() {
                        if (!tabcontrol._maximized) {
                            tabcontrol.maximize();
                        } else {
                            tabcontrol.maximizeRestore();
                        }
                    }
                }
            });
            tabcontrol._maximizeButton = button;
            tabcontrol.addRightToolButton(button);
        }
    });
