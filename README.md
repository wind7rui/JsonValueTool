示例:

json = {"name":"刘禅","age":"6","father":{"name":"刘备","age":"50","properties": {"country": {"id": "002","name": "蜀"},"education": {"description": "学历","value": "私塾"},
"job":[{"id":"1","position":"大王"}],"wife": {"hasOne": false,"list": ["糜夫人","孙夫人","甘皇后","吴夫人"]}}}}

获取job中的position值：
JsonUtils.getValueByKeyExpression(json, "father", "father#properties#job#position")
