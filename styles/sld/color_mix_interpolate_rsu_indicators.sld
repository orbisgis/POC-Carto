<?xml version="1.0" encoding="UTF-8"?>
<UserStyle xmlns="http://www.opengis.net/sld" xmlns:sld="http://www.opengis.net/sld"
           xmlns:gml="http://www.opengis.net/gml" xmlns:ogc="http://www.opengis.net/ogc">
    <Name>Default Styler</Name>
    <FeatureTypeStyle>
        <Rule>
            <PolygonSymbolizer>
                <Fill>
                    <CssParameter name="fill">
                        <ogc:Function name="mix">
                            <ogc:Function name="Interpolate">
                            <ogc:PropertyName>IMPERVIOUS_FRACTION</ogc:PropertyName>
                            <ogc:Literal>0</ogc:Literal>
                            <ogc:Literal>#eeeeee</ogc:Literal>
                            <ogc:Literal>0.5</ogc:Literal>
                            <ogc:Literal>#ff71c1</ogc:Literal>
                                <ogc:Literal>1</ogc:Literal>
                                <ogc:Literal>#9855d9</ogc:Literal>
                            <ogc:Literal>color</ogc:Literal>
                            </ogc:Function>
                            <ogc:Function name="Interpolate">
                                <ogc:PropertyName>VEGETATION_FRACTION</ogc:PropertyName>
                                <ogc:Literal>0</ogc:Literal>
                                <ogc:Literal>#eeeeee</ogc:Literal>
                                <ogc:Literal>0.5</ogc:Literal>
                                <ogc:Literal>#8fce00</ogc:Literal>
                                <ogc:Literal>1</ogc:Literal>
                                <ogc:Literal>#38761d</ogc:Literal>
                                <ogc:Literal>color</ogc:Literal>
                            </ogc:Function>
                            <ogc:Literal>0.5</ogc:Literal>
                        </ogc:Function>
                    </CssParameter>
                </Fill>
                <Stroke>
                    <CssParameter name="stroke">
                        <ogc:Literal>#ffffff</ogc:Literal>
                    </CssParameter>
                    <CssParameter name="stroke-width">
                        <ogc:Literal>1</ogc:Literal>
                    </CssParameter>
                </Stroke>
            </PolygonSymbolizer>
        </Rule>
</FeatureTypeStyle>
        </UserStyle>