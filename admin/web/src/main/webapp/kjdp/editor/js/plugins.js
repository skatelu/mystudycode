define(function (require, exports, module) {
    return {
        "plugin": [
            {
                "name": "Signavio.Plugins.Loading"
            },
            {
                "name": "ORYX.Plugins.Toolbar"
            },
            {
                "name": "ORYX.Plugins.ShapeMenuPlugin",
                "property": [
                    {
                        "group": "Alignment",
                        "align": "Oryx_Top"
                    },
                    {
                        "group": "Edit",
                        "align": "Oryx_Left"
                    }
                ]
            },
            {
                "name": "ORYX.Plugins.ShapeRepository",
            },
            {
                "name": "ORYX.Plugins.PropertyWindow",
            },
            {
                "name": "ORYX.Plugins.Loading",
            },
            {
                "name": "ORYX.Plugins.CanvasResize",
                "notUsesIn":{
                    "namespaces": ["http://b3mn.org/stencilset/xforms#"]
                }

            },
            {
                "name": "ORYX.Plugins.RenameShapes",
            },
            {
                "name": "ORYX.Plugins.Undo",
            },
            {
                "name": "ORYX.Plugins.ProcessLink",
                "requires": {
                    "namespaces": ["http://b3mn.org/stencilset/bpmn1.1#"]
                }

            },
            {
                "name": "ORYX.Plugins.Arrangement",
                "notUsesIn":{
                    "namespaces": ["http://b3mn.org/stencilset/xforms#"]
                }

            },
            {
                "name": "ORYX.Plugins.Save"
            },
            {
                "name": "ORYX.Plugins.View"
            },
            {
                "name": "ORYX.Plugins.DragDropResize"
            },
            {
                "name": "ORYX.Plugins.HighlightingSelectedShapes"
            },
            {
                "name": "ORYX.Plugins.DragDocker",
                "notUsesIn": {
                    "namespaces": ["http://b3mn.org/stencilset/xforms#"]
                }

            },
            {
                "name": "ORYX.Plugins.AddDocker",
                "notUsesIn":{
                    "namespaces": ["http://b3mn.org/stencilset/xforms#"]
                }
            },
            {
                "name": "ORYX.Plugins.SelectionFrame",
                "notUsesIn":{
                    "namespaces": ["http://b3mn.org/stencilset/xforms#"]
                }

            },
            {
                "name": "ORYX.Plugins.ShapeHighlighting"
            },
            {
                "name": "ORYX.Plugins.Overlay"
            },
            {
                "name": "ORYX.Plugins.Edit"
            },
            {
                "name": "ORYX.Plugins.KeysMove"
            },
            {
                "name": "ORYX.Plugins.Layouter.EdgeLayouter"
            },
            {
                "name": "ORYX.Plugins.BPMN2_0",
                "requires":{
                    "namespaces": ["http://b3mn.org/stencilset/bpmn2.0#"]
                 }

            }
        ],

        "properties": [
            {
                "index": 1,
                "group": "File"
            },
            {
                "index": 2,
                "group": "Edit"
            },
            {
                "index": 3,
                "group": "Undo"
            },
            {
                "index": 4,
                "group": "Alignment"
            },
            {
                "index": 5,
                "group": "Group"
            },
            {
                "index": 6,
                "group": "Z-Order"
            },
            {
                "index": 7,
                "group": "Docker"
            },
            {
                "index": 8,
                "group": "Zoom"
            }
        ]
    }

});