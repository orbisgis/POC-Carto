<?xml version="1.0" encoding="UTF-8"?>
<UserStyle xmlns="http://www.opengis.net/sld" xmlns:sld="http://www.opengis.net/sld"
           xmlns:gml="http://www.opengis.net/gml" xmlns:ogc="http://www.opengis.net/ogc">
    <Name>Default Styler</Name>
      <Name>road</Name>
      <FeatureTypeStyle>
        <Rule>
          <Name>highway_link</Name>
          <Description>
            <Title>highway_link</Title>
          </Description>
          <ogc:Filter>
            <ogc:PropertyIsEqualTo>
              <ogc:PropertyName>TYPE</ogc:PropertyName>
              <ogc:Literal>highway_link</ogc:Literal>
            </ogc:PropertyIsEqualTo>
          </ogc:Filter>
          <LineSymbolizer>
            <Stroke>
              <CssParameter name="stroke">#0e43cb</CssParameter>
              <CssParameter name="stroke-width">
                  <ogc:Function name="Interpolate">
                      <ogc:PropertyName>WIDTH</ogc:PropertyName>
                      <ogc:Literal>0</ogc:Literal>
                      <ogc:Literal>1</ogc:Literal>
                      <ogc:Literal>3</ogc:Literal>
                      <ogc:Literal>5</ogc:Literal>
                      <ogc:Literal>16</ogc:Literal>
                      <ogc:Literal>10</ogc:Literal>
                      <ogc:Literal>numeric</ogc:Literal>
                      <ogc:Literal>linear</ogc:Literal>
                  </ogc:Function>
              </CssParameter>
            </Stroke>
          </LineSymbolizer>
        </Rule>
        <Rule>
          <Name>pedestrian</Name>
          <Description>
            <Title>pedestrian</Title>
          </Description>
          <ogc:Filter>
            <ogc:PropertyIsEqualTo>
              <ogc:PropertyName>TYPE</ogc:PropertyName>
              <ogc:Literal>pedestrian</ogc:Literal>
            </ogc:PropertyIsEqualTo>
          </ogc:Filter>
          <LineSymbolizer>
            <Stroke>
              <CssParameter name="stroke">#d8265b</CssParameter>
              <CssParameter name="stroke-width">
                <ogc:Function name="Interpolate">
                  <ogc:PropertyName>WIDTH</ogc:PropertyName>
                  <ogc:Literal>0</ogc:Literal>
                  <ogc:Literal>1</ogc:Literal>
                  <ogc:Literal>3</ogc:Literal>
                  <ogc:Literal>5</ogc:Literal>
                  <ogc:Literal>16</ogc:Literal>
                  <ogc:Literal>10</ogc:Literal>
                  <ogc:Literal>numeric</ogc:Literal>
                  <ogc:Literal>linear</ogc:Literal>
                </ogc:Function>
              </CssParameter>
            </Stroke>
          </LineSymbolizer>
        </Rule>
        <Rule>
          <Name>primary</Name>
          <Description>
            <Title>primary</Title>
          </Description>
          <ogc:Filter>
            <ogc:PropertyIsEqualTo>
              <ogc:PropertyName>TYPE</ogc:PropertyName>
              <ogc:Literal>primary</ogc:Literal>
            </ogc:PropertyIsEqualTo>
          </ogc:Filter>
          <LineSymbolizer>
            <Stroke>
              <CssParameter name="stroke">#ff6011</CssParameter>
              <CssParameter name="stroke-width"><ogc:Function name="Interpolate">
                <ogc:PropertyName>WIDTH</ogc:PropertyName>
                <ogc:Literal>0</ogc:Literal>
                <ogc:Literal>0.1</ogc:Literal>
                <ogc:Literal>3</ogc:Literal>
                <ogc:Literal>5</ogc:Literal>
                <ogc:Literal>16</ogc:Literal>
                <ogc:Literal>10</ogc:Literal>
                <ogc:Literal>numeric</ogc:Literal>
                <ogc:Literal>linear</ogc:Literal>
              </ogc:Function></CssParameter>
            </Stroke>
          </LineSymbolizer>
        </Rule>
        <Rule>
          <Name>residential</Name>
          <Description>
            <Title>residential</Title>
          </Description>
          <ogc:Filter>
            <ogc:PropertyIsEqualTo>
              <ogc:PropertyName>TYPE</ogc:PropertyName>
              <ogc:Literal>residential</ogc:Literal>
            </ogc:PropertyIsEqualTo>
          </ogc:Filter>
          <LineSymbolizer>
            <Stroke>
              <CssParameter name="stroke">#000000</CssParameter>
              <CssParameter name="stroke-width">
                <ogc:Function name="Interpolate">
                <ogc:PropertyName>WIDTH</ogc:PropertyName>
                <ogc:Literal>0</ogc:Literal>
                <ogc:Literal>1</ogc:Literal>
                <ogc:Literal>3</ogc:Literal>
                <ogc:Literal>5</ogc:Literal>
                <ogc:Literal>16</ogc:Literal>
                <ogc:Literal>10</ogc:Literal>
                <ogc:Literal>numeric</ogc:Literal>
                <ogc:Literal>linear</ogc:Literal>
                </ogc:Function></CssParameter>
            </Stroke>
          </LineSymbolizer>
        </Rule>
        <Rule>
          <Name>secondary</Name>
          <Description>
            <Title>secondary</Title>
          </Description>
          <ogc:Filter>
            <ogc:PropertyIsEqualTo>
              <ogc:PropertyName>TYPE</ogc:PropertyName>
              <ogc:Literal>secondary</ogc:Literal>
            </ogc:PropertyIsEqualTo>
          </ogc:Filter>
          <LineSymbolizer>
            <Stroke>
              <CssParameter name="stroke">#db1e2a</CssParameter>
              <CssParameter name="stroke-width"><ogc:Function name="Interpolate">
                <ogc:PropertyName>WIDTH</ogc:PropertyName>
                <ogc:Literal>0</ogc:Literal>
                <ogc:Literal>0.1</ogc:Literal>
                <ogc:Literal>3</ogc:Literal>
                <ogc:Literal>5</ogc:Literal>
                <ogc:Literal>16</ogc:Literal>
                <ogc:Literal>10</ogc:Literal>
                <ogc:Literal>numeric</ogc:Literal>
                <ogc:Literal>linear</ogc:Literal>
              </ogc:Function></CssParameter>
            </Stroke>
          </LineSymbolizer>
        </Rule>
        <Rule>
          <Name>tertiary</Name>
          <Description>
            <Title>tertiary</Title>
          </Description>
          <ogc:Filter>
            <ogc:PropertyIsEqualTo>
              <ogc:PropertyName>TYPE</ogc:PropertyName>
              <ogc:Literal>tertiary</ogc:Literal>
            </ogc:PropertyIsEqualTo>
          </ogc:Filter>
          <LineSymbolizer>
            <Stroke>
              <CssParameter name="stroke">#711671</CssParameter>
              <CssParameter name="stroke-width"><ogc:Function name="Interpolate">
                <ogc:PropertyName>WIDTH</ogc:PropertyName>
                <ogc:Literal>0</ogc:Literal>
                <ogc:Literal>1</ogc:Literal>
                <ogc:Literal>3</ogc:Literal>
                <ogc:Literal>5</ogc:Literal>
                <ogc:Literal>16</ogc:Literal>
                <ogc:Literal>10</ogc:Literal>
                <ogc:Literal>numeric</ogc:Literal>
                <ogc:Literal>linear</ogc:Literal>
              </ogc:Function></CssParameter>
            </Stroke>
          </LineSymbolizer>
        </Rule>
        <Rule>
          <Name>track</Name>
          <Description>
            <Title>track</Title>
          </Description>
          <ogc:Filter>
            <ogc:PropertyIsEqualTo>
              <ogc:PropertyName>TYPE</ogc:PropertyName>
              <ogc:Literal>track</ogc:Literal>
            </ogc:PropertyIsEqualTo>
          </ogc:Filter>
          <LineSymbolizer>
            <Stroke>
              <CssParameter name="stroke">#711671</CssParameter>
              <CssParameter name="stroke-width"><ogc:Function name="Interpolate">
                <ogc:PropertyName>WIDTH</ogc:PropertyName>
                <ogc:Literal>0</ogc:Literal>
                <ogc:Literal>1</ogc:Literal>
                <ogc:Literal>3</ogc:Literal>
                <ogc:Literal>5</ogc:Literal>
                <ogc:Literal>16</ogc:Literal>
                <ogc:Literal>10</ogc:Literal>
                <ogc:Literal>numeric</ogc:Literal>
                <ogc:Literal>linear</ogc:Literal>
              </ogc:Function></CssParameter>
            </Stroke>
          </LineSymbolizer>
        </Rule>
        <Rule>
          <Name>trunk</Name>
          <Description>
            <Title>trunk</Title>
          </Description>
          <ogc:Filter>
            <ogc:PropertyIsEqualTo>
              <ogc:PropertyName>TYPE</ogc:PropertyName>
              <ogc:Literal>trunk</ogc:Literal>
            </ogc:PropertyIsEqualTo>
          </ogc:Filter>
          <LineSymbolizer>
            <Stroke>
              <CssParameter name="stroke">#c2ec1c</CssParameter>
              <CssParameter name="stroke-width"><ogc:Function name="Interpolate">
                <ogc:PropertyName>WIDTH</ogc:PropertyName>
                <ogc:Literal>0</ogc:Literal>
                <ogc:Literal>1</ogc:Literal>
                <ogc:Literal>3</ogc:Literal>
                <ogc:Literal>5</ogc:Literal>
                <ogc:Literal>16</ogc:Literal>
                <ogc:Literal>10</ogc:Literal>
                <ogc:Literal>numeric</ogc:Literal>
                <ogc:Literal>linear</ogc:Literal>
              </ogc:Function></CssParameter>
            </Stroke>
          </LineSymbolizer>
        </Rule>
      </FeatureTypeStyle>
    </UserStyle>